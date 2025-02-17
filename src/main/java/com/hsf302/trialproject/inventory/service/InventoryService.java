package com.hsf302.trialproject.inventory.service;


import com.hsf302.trialproject.inventory.dto.InventoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface InventoryService {
    Page<InventoryDTO> findPaginatedInventories(int pageNumber, int pageSize);
    Page<InventoryDTO> findPaginatedInventoriesByOwnerId(Long ownerId, Pageable pageable);
    List<InventoryDTO> findAllInventoriesByOwnerId(Long ownerId);
    Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndNameContaining(Long ownerId, String name, int pageNumber, int pageSize);
    InventoryDTO findInventoryById(Long id);
    void saveInventory(InventoryDTO inventoryDTO);
    void deleteInventoryById(Long id);
    boolean isExistInventoryByOwnerIdAndName(Long ownerId, String name);
    Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndNameContaining(Long ownerId, String name, Pageable pageable);
    Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndLocationContaining(Long ownerId, String location, Pageable pageable);
}
