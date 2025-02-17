package com.hsf302.trialproject.inventory.service;

import com.hsf302.trialproject.inventory.dto.ZoneDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ZoneService {
    Page<ZoneDTO> findPaginatedZones(int pageNumber, int pageSize);
    Page<ZoneDTO> findPaginatedZonesByInventoryId(Long inventoryId, Pageable pageable);
    Page<ZoneDTO> findPaginatedZonesByInventoryIdAndNameContaining(Long inventoryId, String name, Pageable pageable);
    Page<ZoneDTO> findPaginatedZonesByInventoryIdAndProductNameContaining(Long inventoryId, String productName, Pageable pageable);

    List<ZoneDTO> findZonesByInventoryId(Long inventoryId);


    ZoneDTO findZoneById(Long id);
    Boolean isExistZoneByInventoryIdAndName(Long inventoryId, String name);
    void saveZone(ZoneDTO zoneDTO);
}
