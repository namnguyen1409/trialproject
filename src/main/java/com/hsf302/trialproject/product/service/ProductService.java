package com.hsf302.trialproject.product.service;

import com.hsf302.trialproject.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductDTO> findPaginatedProducts(int pageNumber, int pageSize);
    Page<ProductDTO> findPaginatedProductsByOwnerId(Long ownerId, Pageable pageable);
    Page<ProductDTO> findPaginatedProductsByOwnerIdAndNameContaining(Long ownerId, String name, Pageable pageable);
    Page<ProductDTO> findPaginatedProductsByOwnerIdAndDescriptionContaining(Long ownerId, String description, Pageable pageable);
    Page<ProductDTO> findPaginatedProductsByNameContaining(String name, Pageable pageable);

    ProductDTO findProductById(Long id);
    void saveProduct(ProductDTO productDTO);
    void deleteProductById(Long id);
    Boolean existsByNameAndCreatedById(String name, Long ownerId);
}
