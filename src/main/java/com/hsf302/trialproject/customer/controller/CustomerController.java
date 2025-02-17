package com.hsf302.trialproject.customer.controller;

import com.hsf302.trialproject.auth.security.CustomUserDetails;
import com.hsf302.trialproject.customer.dto.CustomerDTO;
import com.hsf302.trialproject.customer.service.CustomerService;
import com.hsf302.trialproject.user.entity.User;
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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

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

    @GetMapping("/list")
    public String list(
            Model model,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "searchBy", required = false, defaultValue = "name") String searchBy,
            @RequestParam(value = "orderBy", required = false, defaultValue = "createdAt") String orderBy,
            @RequestParam(value = "direction", required = false, defaultValue = "desc") String direction
    ) {
        List<String> fields = Arrays.asList("fullName", "phone", "email", "address", "balance");
        Map<String, String> fieldTitles = createPairs(fields, Arrays.asList("Họ và tên", "Số điện thoại", "Email", "Địa chỉ", "số dư"));
        Map<String, String> fieldClasses = createPairs(fields, Arrays.asList("", "phone", "", "", ""));
        List<String> searchAbleFields = Arrays.asList("fullName", "phone", "email", "address");
        model.addAttribute("fields", fields);
        model.addAttribute("fieldTitles", fieldTitles);
        model.addAttribute("fieldClasses", fieldClasses);
        model.addAttribute("searchAbleFields", searchAbleFields);
        if (!fields.contains(searchBy)) {
            searchBy = "fullName";
        }
        if (!fields.contains(orderBy)) {
            orderBy = "createdAt";
        }
        Sort sortDirection = "asc".equalsIgnoreCase(direction)
                ? Sort.by(orderBy).ascending()
                : Sort.by(orderBy).descending();
        Pageable pageable = PageRequest.of(page - 1, size, sortDirection);
        Page<CustomerDTO> customers;
        if (search != null && !search.isEmpty()) {
            customers = switch (searchBy) {
                case "fullName" -> customerService.findPaginatedCustomersByCreatedByIdAndFullNameContaining(getUser().getId(), search, pageable);
                case "phone" -> customerService.findPaginatedCustomersByCreatedByIdAndPhoneContaining(getUser().getId(), search, pageable);
                case "email" -> customerService.findPaginatedCustomersByCreatedByIdAndEmailContaining(getUser().getId(), search, pageable);
                case "address" -> customerService.findPaginatedCustomersByCreatedByIdAndAddressContaining(getUser().getId(), search, pageable);
                default -> customerService.findPaginatedCustomersByCreatedById(getUser().getId(), pageable);
            };
        } else {
            customers = customerService.findPaginatedCustomersByCreatedById(getUser().getId(), pageable);
        }
        model.addAttribute("customers", customers);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        model.addAttribute("orderBy", orderBy);
        model.addAttribute("searchBy", searchBy);
        model.addAttribute("direction", direction);
        return "customer/list";
    }


    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("customer", new CustomerDTO());
        return "customer/add";
    }

    @PostMapping("/add")
    public String add(
            @Validated @ModelAttribute("customer") CustomerDTO customerDTO,
            BindingResult bindingResult
    ) {

        if(Boolean.TRUE.equals(customerService.existByPhone(customerDTO.getPhone()))) {
            bindingResult.rejectValue("phone", "error.phone", "Số điện thoại đã tồn tại");
        }
        if(Boolean.TRUE.equals(customerService.existByEmail(customerDTO.getEmail()))) {
            bindingResult.rejectValue("email", "error.email", "Email đã tồn tại");
        }
        if (bindingResult.hasErrors()) {
            return "customer/add";
        }
        customerService.saveCustomer(customerDTO);
        return "redirect:/customer/list";
    }


}
