package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.exception.UnhandledRoleException;
import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.service.InventoryService;
import com.hsf302.trialproject.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private static final Logger logger = LoggerFactory.getLogger(InvoiceController.class);
    private final InventoryService inventoryService;
    private static final String UNHANDLED_ROLE_MESSAGE = "Unhandled role";


    @GetMapping("/import")
    public String importInvoice(
            Model model
    ) {
        User u = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();

        if ("STAFF".equals(u.getRole().toString())) {
            model.addAttribute("inventory", inventoryService.findInventoryById(u.getAssignedInventory().getId()));
        }
        else if("OWNER".equals(u.getRole().toString())) {
            model.addAttribute("inventories", inventoryService.findAllInventoriesByOwnerId(u.getId()));
        }
        else if (u.getRole() != null) {
            logger.error(UNHANDLED_ROLE_MESSAGE);
            throw new UnhandledRoleException(UNHANDLED_ROLE_MESSAGE);
        }
        return "invoice/import";
    }

    @GetMapping("/export")
    public String exportInvoice(
            Model model
    ) throws Exception {
        User u = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).getUser();

        if ("STAFF".equals(u.getRole().toString())) {
            model.addAttribute("inventory", inventoryService.findInventoryById(u.getAssignedInventory().getId()));
        }
        else if("OWNER".equals(u.getRole().toString())) {
            model.addAttribute("inventories", inventoryService.findAllInventoriesByOwnerId(u.getId()));
        }
        else if (u.getRole() != null) {
            logger.error(UNHANDLED_ROLE_MESSAGE);
            throw new UnhandledRoleException(UNHANDLED_ROLE_MESSAGE);
        }
        return "invoice/export";
    }


}
