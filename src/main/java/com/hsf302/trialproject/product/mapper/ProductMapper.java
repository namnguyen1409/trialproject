package com.hsf302.trialproject.product.mapper;

import com.hsf302.trialproject.product.dto.ProductDTO;
import com.hsf302.trialproject.product.entity.Product;
import com.hsf302.trialproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ProductDTO mapToProductDTO(@NotNull Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product mapToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

}
