package com.hsf302.trialproject.invoice.controller;

import com.hsf302.trialproject.auth.security.CustomUserDetails;
import com.hsf302.trialproject.inventory.service.InventoryService;
import com.hsf302.trialproject.user.entity.User;
import com.hsf302.trialproject.user.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InventoryService inventoryService;


    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping("/import")
    public String importInvoice(
            Model model
    ) {
        User u = getUser();

        if (u.getRole().equals(RoleType.STAFF)) {
            model.addAttribute("inventory", inventoryService.findInventoryById(u.getAssignedInventory().getId()));
        }
        else if(u.getRole().equals(RoleType.OWNER)) {
            model.addAttribute("inventories", inventoryService.findAllInventoriesByOwnerId(u.getId()));
        }

        return "invoice/import";
    }


}
