package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.exception.Http404;
import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.util.XSSProtectedUtil;
import com.hsf302.trialproject.dto.InventoryDTO;
import com.hsf302.trialproject.dto.ZoneDTO;
import com.hsf302.trialproject.service.InventoryService;
import com.hsf302.trialproject.service.ZoneService;
import com.hsf302.trialproject.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inventory")
@AllArgsConstructor
@Transactional
public class InventoryController {

    private final InventoryService inventoryService;
    private final ZoneService zoneService;
    private final XSSProtectedUtil xssProtectedUtil;

    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }


    private Map<String, String> createPairs(List<String> fields, List<String> fieldTitles) {
        Map<String, String> pairs = new HashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            pairs.put(fields.get(i), fieldTitles.get(i));
        }
        return pairs;
    }


    @GetMapping("/add")
    public String addInventory(Model model) {
        model.addAttribute("inventory", new InventoryDTO());
        return "inventory/add";
    }

    @PostMapping("/add")
    public String addInventory(
            @ModelAttribute("inventory")  @Validated InventoryDTO inventoryDTO,
            BindingResult bindingResult
            ) {
        if (inventoryService.isExistInventoryByOwnerIdAndName(getUser().getId(), inventoryDTO.getName())) {
            bindingResult.rejectValue("name", "error.inventory", "Tên kho đã tồn tại");
        }
        if (bindingResult.hasErrors()) {
            return "inventory/add";
        }
        inventoryDTO.setLocation(xssProtectedUtil.encodeAllHTMLElement(inventoryDTO.getLocation()));
        inventoryDTO.setDescription(xssProtectedUtil.sanitize(inventoryDTO.getDescription()));
        inventoryDTO.setCreatedAt(LocalDateTime.now());
        inventoryService.saveInventory(inventoryDTO);
        return "redirect:/inventory";
    }

    @GetMapping({"", "/", "/list"})
    public String listInventory(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "searchBy", required = false, defaultValue = "name") String searchBy,
            @RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction
    ) {

        List<String> fields = Arrays.asList("name", "location", "createdAt");
        if (!fields.contains(searchBy)) {
            searchBy = "name";
        }
        if (!fields.contains(orderBy)) {
            orderBy = "createdAt";
        }

        Map<String, String> fieldTitles = createPairs(fields, Arrays.asList("Tên kho", "Địa chỉ", "Ngày tạo"));
        Map<String, String> fieldClasses = createPairs(fields, Arrays.asList("", "", "dateTime"));
        List<String> searchAbleFields = Arrays.asList("name", "location");


        model.addAttribute("fields", fields);
        model.addAttribute("fieldTitles", fieldTitles);
        model.addAttribute("fieldClasses", fieldClasses);
        model.addAttribute("searchAbleFields", searchAbleFields);

        // Configure sorting
        Sort sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sortDirection);

        // Retrieve paginated inventories
        Page<InventoryDTO> inventories;
        if (search != null && !search.isEmpty()) {
            inventories = switch (searchBy) {
                case "name" -> inventoryService.findPaginatedInventoriesByOwnerIdAndNameContaining(
                        getUser().getId(), search, pageable);
                case "location" -> inventoryService.findPaginatedInventoriesByOwnerIdAndLocationContaining(
                        getUser().getId(), search, pageable);
                default -> inventoryService.findPaginatedInventoriesByOwnerId(getUser().getId(), pageable);
            };
        } else {
            inventories = inventoryService.findPaginatedInventoriesByOwnerId(getUser().getId(), pageable);
        }

        model.addAttribute("inventories", inventories);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("direction", direction);

        return "inventory/list";
    }


    @GetMapping("/detail/{id}")
    public String detailInventory(@PathVariable("id") Long id, Model model) {
        InventoryDTO inventory = inventoryService.findInventoryById(id);

        if (inventory == null) {
            throw new Http404("Không tìm thấy kho hàng mà bạn yêu cầu");
        }

        if (!inventory.getCreatedBy().equals(getUser().getId())) {
            throw new Http404("Bạn không có quyền truy cập kho hàng này");
        }

        model.addAttribute("inventory", inventory);
        return "inventory/detail";
    }


    @GetMapping("/{id}/zone")
    public String listZoneByInventoryId(
            @PathVariable("id") Long id,
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "searchBy", required = false, defaultValue = "name") String searchBy,
            @RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction
    ) {
        InventoryDTO inventory = inventoryService.findInventoryById(id);

        if (inventory == null) {
            throw new Http404("Không tìm thấy kho hàng mà bạn yêu cầu");
        }

        if (!inventory.getCreatedBy().equals(getUser().getId())) {
            throw new Http404("Bạn không có quyền truy cập kho hàng này");
        }

        List<String> fields = Arrays.asList("name", "productName", "productImage", "quantity", "createdAt");
        Map<String, String> fieldTitles = createPairs(fields, Arrays.asList("Tên khu vực", "Tên sản phẩm", "Hình ảnh", "Tồn kho", "Ngày tạo"));
        Map<String, String> fieldClasses = createPairs(fields, Arrays.asList("", "", "image", "", "dateTime"));
        List<String> searchAbleFields = Arrays.asList("name", "productName");
        model.addAttribute("fields", fields);
        model.addAttribute("fieldTitles", fieldTitles);
        model.addAttribute("fieldClasses", fieldClasses);
        model.addAttribute("searchAbleFields", searchAbleFields);
        Sort sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sortDirection);
        Page<ZoneDTO> zones;
        if (search != null && !search.isEmpty()) {
            zones = switch (searchBy) {
                case "name" -> zoneService.findPaginatedZonesByInventoryIdAndNameContaining(
                        id, search, pageable);
                case "productName" -> zoneService.findPaginatedZonesByInventoryIdAndProductNameContaining(
                        id, search, pageable);
                default -> zoneService.findPaginatedZonesByInventoryId(id, pageable);
            };
        } else {
            zones = zoneService.findPaginatedZonesByInventoryId(id, pageable);
        }
        model.addAttribute("zones", zones);
        model.addAttribute("inventory", inventory);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("direction", direction);
        return "inventory/zone/list";
    }

    @GetMapping("/{id}/zone/add")
    public String addZone(@PathVariable("id") Long id, Model model) {
        InventoryDTO inventory = inventoryService.findInventoryById(id);

        if (inventory == null) {
            throw new Http404("Không tìm thấy kho hàng mà bạn yêu cầu");
        }

        if (!inventory.getCreatedBy().equals(getUser().getId())) {
            throw new Http404("Bạn không có quyền truy cập kho hàng này");
        }

        ZoneDTO zone = new ZoneDTO();
        zone.setInventoryId(id);
        model.addAttribute("zone", zone);
        return "inventory/zone/add";
    }

    @PostMapping("/{id}/zone/add")
    public String addZone(
            @PathVariable("id") Long id,
            @ModelAttribute("zone") @Validated ZoneDTO zoneDTO,
            BindingResult bindingResult
    ) {
        InventoryDTO inventory = inventoryService.findInventoryById(id);

        if (inventory == null) {
            throw new Http404("Không tìm thấy kho hàng mà bạn yêu cầu");
        }

        if (!inventory.getCreatedBy().equals(getUser().getId())) {
            throw new Http404("Bạn không có quyền truy cập kho hàng này");
        }

        if (Boolean.TRUE.equals(zoneService.isExistZoneByInventoryIdAndName(id, zoneDTO.getName()))) {
            bindingResult.rejectValue("name", "error.zone", "Tên khu vực đã tồn tại");
        }

        if (bindingResult.hasErrors()) {
            return "inventory/zone/add";
        }
        zoneDTO.setId(null);
        zoneDTO.setInventoryId(id);
        zoneService.saveZone(zoneDTO);
        return "redirect:/inventory/" + id + "/zone";
    }



    @GetMapping("/edit/{id}")
    public String editInventory(@PathVariable("id") Long id, Model model) {
        InventoryDTO inventory = inventoryService.findInventoryById(id);

        if (inventory == null) {
            throw new Http404("Không tìm thấy kho hàng mà bạn yêu cầu");
        }

        if (!inventory.getCreatedBy().equals(getUser().getId())) {
            throw new Http404("Bạn không có quyền truy cập kho hàng này");
        }

        model.addAttribute("inventory", inventory);
        return "inventory/edit";
    }


}
