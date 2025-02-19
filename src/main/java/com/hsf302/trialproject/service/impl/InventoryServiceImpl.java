package com.hsf302.trialproject.service.impl;

import com.hsf302.trialproject.dto.InventoryDTO;
import com.hsf302.trialproject.entity.Inventory;
import com.hsf302.trialproject.exception.InventoryNoSuchElementException;
import com.hsf302.trialproject.mapper.InventoryMapper;
import com.hsf302.trialproject.repository.InventoryRepository;
import com.hsf302.trialproject.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepository inventoryRepository;
    private InventoryMapper inventoryMapper;
    private MessageSource messageSource;

    @Override
    public Page<InventoryDTO> findPaginatedInventories(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Inventory> page = inventoryRepository.findAll(pageable);
        return page.map(inventoryMapper::mapToInventoryDTO);
    }

    @Override
    public Page<InventoryDTO> findPaginatedInventoriesByOwnerId(Long ownerId, Pageable pageable) {
        Page<Inventory> page = inventoryRepository.findByCreatedById(ownerId, pageable);
        return page.map(inventoryMapper::mapToInventoryDTO);
    }

    @Override
    public List<InventoryDTO> findAllInventoriesByOwnerId(Long ownerId) {
        List<Inventory> inventories = inventoryRepository.findByCreatedById(ownerId);
        return inventories.stream().map(inventoryMapper::mapToInventoryDTO).toList();
    }

    @Override
    public Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndNameContaining(Long ownerId, String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Inventory> page = inventoryRepository.findByCreatedByIdAndNameContaining(ownerId, name, pageable);
        return page.map(inventoryMapper::mapToInventoryDTO);
    }

    @Override
    public InventoryDTO findInventoryById(Long id) {
        try {
            Optional<Inventory> inventoryOptional = inventoryRepository.findById(id);
            return inventoryOptional.map(inventory -> inventoryMapper.mapToInventoryDTO(inventory)).orElse(null);
        } catch (Exception exception) {
            String message = messageSource.getMessage("entity.notfound", new Object[]{id}, Locale.getDefault());
            throw new InventoryNoSuchElementException(message, id);
        }
    }

    @Override
    public void saveInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = inventoryMapper.mapToInventory(inventoryDTO);
        inventoryRepository.save(inventory);
    }


    @Override
    public void deleteInventoryById(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public boolean isExistInventoryByOwnerIdAndName(Long ownerId, String name) {
        return inventoryRepository.findByCreatedByIdAndName(ownerId, name) != null;
    }

    @Override
    public Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndNameContaining(Long ownerId, String name, Pageable pageable) {
        return inventoryRepository.findByCreatedByIdAndLocationContaining(ownerId, name, pageable).map(inventoryMapper::mapToInventoryDTO);
    }

    @Override
    public Page<InventoryDTO> findPaginatedInventoriesByOwnerIdAndLocationContaining(Long ownerId, String location, Pageable pageable) {
        return inventoryRepository.findByCreatedByIdAndLocationContaining(ownerId, location, pageable).map(inventoryMapper::mapToInventoryDTO);
    }
}
