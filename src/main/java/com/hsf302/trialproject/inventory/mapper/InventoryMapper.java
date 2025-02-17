package com.hsf302.trialproject.inventory.mapper;

import com.hsf302.trialproject.inventory.dto.InventoryDTO;
import com.hsf302.trialproject.inventory.entity.Inventory;
import com.hsf302.trialproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryMapper {
    private final ModelMapper modelMapper;

    public InventoryDTO mapToInventoryDTO(Inventory inventory) {
        return modelMapper.map(inventory, InventoryDTO.class);
    }

    public Inventory mapToInventory(InventoryDTO inventoryDTO) {
        return modelMapper.map(inventoryDTO, Inventory.class);
    }
}
