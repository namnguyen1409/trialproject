package com.hsf302.trialproject.inventory.mapper;

import com.hsf302.trialproject.inventory.dto.ZoneDTO;
import com.hsf302.trialproject.inventory.entity.Zone;
import com.hsf302.trialproject.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ZoneMapper {

    private final InventoryRepository inventoryRepository;
    private final ModelMapper modelMapper;


    public ZoneDTO mapToZoneDTO(Zone zone) {
        return modelMapper.map(zone, ZoneDTO.class);
    }

    public Zone mapToZone(ZoneDTO zoneDTO) {
        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        if (zoneDTO.getInventoryId() != null) {
            zone.setInventory(inventoryRepository.findById(zoneDTO.getInventoryId()).orElse(null));
        }
        return zone;
    }

}
