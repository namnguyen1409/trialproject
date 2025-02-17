package com.hsf302.trialproject.inventory.repository;


import com.hsf302.trialproject.inventory.entity.Zone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Page<Zone> findByInventoryId(Long inventoryId, Pageable pageable);
    Page<Zone> findByInventoryIdAndNameContaining(Long inventoryId, String name, Pageable pageable);
    Page<Zone> findByInventoryIdAndProductNameContaining(Long inventoryId, String productName, Pageable pageable);
    List<Zone> findByInventoryId(Long inventoryId);
    Boolean existsByInventoryIdAndName(Long inventoryId, String name);
}
