package com.hsf302.trialproject.common.config;

import com.hsf302.trialproject.customer.dto.CustomerDTO;
import com.hsf302.trialproject.customer.entity.Customer;
import com.hsf302.trialproject.inventory.dto.InventoryDTO;
import com.hsf302.trialproject.inventory.dto.ZoneDTO;
import com.hsf302.trialproject.inventory.entity.Inventory;
import com.hsf302.trialproject.inventory.entity.Zone;
import com.hsf302.trialproject.inventory.repository.InventoryRepository;
import com.hsf302.trialproject.product.dto.ProductDTO;
import com.hsf302.trialproject.product.entity.Product;
import com.hsf302.trialproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<Product, ProductDTO>() {
            @Override
            protected void configure() {
                map().setCreatedBy(source.getCreatedBy().getId());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedBy(source.getUpdatedBy().getId());
                map().setUpdatedAt(source.getUpdatedAt());
            }
        });

        modelMapper.addMappings(new PropertyMap<Inventory, InventoryDTO>() {
            @Override
            protected void configure() {
                map().setCreatedBy(source.getCreatedBy().getId());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedBy(source.getUpdatedBy().getId());
                map().setUpdatedAt(source.getUpdatedAt());
            }
        });

        modelMapper.addMappings(new PropertyMap<Zone, ZoneDTO>() {
            @Override
            protected void configure() {
                map().setCreatedBy(source.getCreatedBy().getId());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedBy(source.getUpdatedBy().getId());
                map().setUpdatedAt(source.getUpdatedAt());
                map().setInventoryId(source.getInventory().getId());
                if(source.getProduct() != null) {
                    map().setProductId(source.getProduct().getId());
                    map().setProductName(source.getProduct().getName());
                    map().setProductImage(source.getProduct().getImage());
                } else {
                    map().setProductId(null);
                    map().setProductName(null);
                    map().setProductImage(null);
                }
            }
        });


        modelMapper.addMappings(new PropertyMap<Customer, CustomerDTO>() {
            @Override
            protected void configure() {
                map().setCreatedBy(source.getCreatedBy().getId());
                map().setCreatedAt(source.getCreatedAt());
                map().setUpdatedBy(source.getUpdatedBy().getId());
                map().setUpdatedAt(source.getUpdatedAt());
            }
        });



        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        return modelMapper;
    }

}
