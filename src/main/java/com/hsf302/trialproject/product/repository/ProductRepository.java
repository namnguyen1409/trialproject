package com.hsf302.trialproject.product.repository;

import com.hsf302.trialproject.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContaining(String name, Pageable pageable);
    Page<Product> findByCreatedById(Long ownerId, Pageable pageable);
    Page<Product> findByCreatedByIdAndNameContainingIgnoreCase(Long ownerId, String name, Pageable pageable);
    Page<Product> findByCreatedByIdAndDescriptionPlainTextContainingIgnoreCase(Long createdById, String descriptionPlainText, Pageable pageable);
    Boolean existsByNameAndCreatedById(String name, Long createdById);
}

