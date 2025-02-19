package com.hsf302.trialproject.mapper;

import com.hsf302.trialproject.dto.InventoryDTO;
import com.hsf302.trialproject.entity.Inventory;
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
