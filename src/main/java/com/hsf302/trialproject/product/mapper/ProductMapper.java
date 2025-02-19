package com.hsf302.trialproject.product.mapper;

import com.hsf302.trialproject.dto.ProductDTO;
import com.hsf302.trialproject.entity.Product;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ModelMapper modelMapper;

    public ProductDTO mapToProductDTO(@NotNull Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product mapToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

}
