package com.hsf302.trialproject.controller;

import com.hsf302.trialproject.security.CustomUserDetails;
import com.hsf302.trialproject.dto.ZoneDTO;
import com.hsf302.trialproject.service.InventoryService;
import com.hsf302.trialproject.service.ZoneService;
import com.hsf302.trialproject.entity.User;
import com.hsf302.trialproject.enums.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryRestController {

    private final InventoryService inventoryService;
    private final ZoneService zoneService;


    private User getUser() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    @GetMapping("/{id}/zones")
    public ResponseEntity<List<ZoneDTO>> getZones(
            @PathVariable("id") Long id
    ) {
        var inventory = inventoryService.findInventoryById(id);
        if (inventory == null) {
            return ResponseEntity.notFound().build();
        }
        var u = getUser();
        if (u.getRole().equals(RoleType.STAFF) && !u.getAssignedInventory().getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }

        if (u.getRole().equals(RoleType.OWNER) && !u.getId().equals(inventory.getCreatedBy())) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(zoneService.findZonesByInventoryId(id));
    }


}
