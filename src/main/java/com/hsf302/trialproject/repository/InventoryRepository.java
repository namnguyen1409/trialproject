package com.hsf302.trialproject.repository;

import com.hsf302.trialproject.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Page<Inventory> findByCreatedById(Long userId, Pageable pageable);
    Page<Inventory> findByCreatedByIdAndNameContaining(Long userId, String name, Pageable pageable);
    Page<Inventory> findByCreatedByIdAndLocationContaining(Long userId, String location, Pageable pageable);
    List<Inventory> findByCreatedById(Long userId);
    Inventory findByCreatedByIdAndName(Long userId, String name);

}
