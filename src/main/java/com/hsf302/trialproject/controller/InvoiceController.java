package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.service.InventoryService;
import com.hsf302.trialproject.entity.User;
import lombok.Data;
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


    @GetMapping("/import")
    public String importInvoice(
            Model model
    ) throws Exception {
        User u = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();

        if (u.getRole().toString() == "STAFF") {
            model.addAttribute("inventory", inventoryService.findInventoryById(u.getAssignedInventory().getId()));
        }
        else if(u.getRole().toString() == "OWNER") {
            model.addAttribute("inventories", inventoryService.findAllInventoriesByOwnerId(u.getId()));
        }
        else if (u.getRole() != null) {
            System.out.println("Unhandled role");
            throw new Exception("Unhandled role");
        }
        return "invoice/import";
    }

    @GetMapping("/export")
    public String exportInvoice(
            Model model
    ) throws Exception {
        User u = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();

        if (u.getRole().toString() == "STAFF") {
            model.addAttribute("inventory", inventoryService.findInventoryById(u.getAssignedInventory().getId()));
        }
        else if(u.getRole().toString() == "OWNER") {
            model.addAttribute("inventories", inventoryService.findAllInventoriesByOwnerId(u.getId()));
        }
        else if (u.getRole() != null) {
            System.out.println("Unhandled role");
            throw new Exception("Unhandled role");
        }
        return "invoice/export";
    }


}
