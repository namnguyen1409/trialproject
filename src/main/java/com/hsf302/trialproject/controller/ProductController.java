package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.service.RecaptchaService;
import com.hsf302.trialproject.service.StorageService;
import com.hsf302.trialproject.util.XSSProtectedUtil;
import com.hsf302.trialproject.dto.ProductDTO;
import com.hsf302.trialproject.product.service.ProductService;
import com.hsf302.trialproject.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

@Controller
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private final RecaptchaService recaptchaService;
    private final ProductService productService;
    private final XSSProtectedUtil xssProtectedUtil;
    private final StorageService storageService;

    private static final String PRICE = "price";

    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    private Map<String, String> createPairs(List<String> fields, List<String> fieldTitles) {
        if (!fields.isEmpty() && fields.size() == fieldTitles.size()) {
            Map<String, String> pairs = new HashMap<>();
            for (int i = 0; i < fields.size(); i++) {
                pairs.put(fields.get(i), fieldTitles.get(i));
            }
            return pairs;
        }
        return Collections.emptyMap();
    }


    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "product/add";
    }

    @PostMapping("/add")
    public String addProduct(
            @ModelAttribute("product") @Validated ProductDTO productDTO,
            BindingResult bindingResult,
            @RequestParam("g-recaptcha-response") String recaptchaResponse,
            RedirectAttributes redirectAttributes
    ) {


        if (productDTO.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            bindingResult.rejectValue(PRICE, "price.error", "Giá bán phải lớn hơn 0");
        }
        if (bindingResult.hasErrors()) {
            return "product/add";
        }
        var image = storageService.moveToUploads(productDTO.getImage());
        productDTO.setImage(image);
        productDTO.setDescription(xssProtectedUtil.sanitize(productDTO.getDescription()));
        productDTO.setDescriptionPlainText(xssProtectedUtil.htmlToPlainText(productDTO.getDescription()));
        productDTO.setCreatedBy(getUser().getId());
        productService.saveProduct(productDTO);
        redirectAttributes.addFlashAttribute("success", "Thêm sản phẩm thành công");
        return "redirect:/product/list";
    }

    @GetMapping({"", "/", "/list"})
    public String listProduct(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "searchBy", required = false, defaultValue = "name") String searchBy,
            @RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction
    ) {
        List<String> fields = Arrays.asList("image", "name",PRICE);
        Map<String, String> fieldTitles = createPairs(fields, Arrays.asList("Hình ảnh", "Tên", "Giá"));
        Map<String, String> fieldClasses = createPairs(fields, Arrays.asList("image", "",PRICE));
        List<String> searchAbleFields = Arrays.asList("name", "description");

        model.addAttribute("fields", fields);
        model.addAttribute("fieldTitles", fieldTitles);
        model.addAttribute("fieldClasses", fieldClasses);
        model.addAttribute("searchAbleFields", searchAbleFields);

        if (!fields.contains(searchBy)) {
            searchBy = "name";
        }

        if (!fields.contains(orderBy)) {
            orderBy = "id";
        }

        Sort sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sortDirection);

        Page<ProductDTO> products;
        if (search != null && !search.isEmpty()) {
             switch (searchBy) {
                case "name" :
                    products = productService.findPaginatedProductsByOwnerIdAndNameContaining(getUser().getId(), search, pageable);
                    break;
                case "description":
                    products = productService.findPaginatedProductsByOwnerIdAndDescriptionContaining(getUser().getId(), search, pageable);
                    break;
                default:
                    products = productService.findPaginatedProductsByOwnerId(getUser().getId(), pageable);
            }
        } else {
            products = productService.findPaginatedProductsByOwnerId(getUser().getId(), pageable);
        }

        model.addAttribute("products", products);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("direction", direction);
        return "product/list";
    }

}
