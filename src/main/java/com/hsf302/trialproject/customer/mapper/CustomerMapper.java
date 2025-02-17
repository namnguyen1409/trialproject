package com.hsf302.trialproject.customer.mapper;

import com.hsf302.trialproject.customer.dto.CustomerDTO;
import com.hsf302.trialproject.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final ModelMapper modelMapper;

    public CustomerDTO mapToCustomerDTO(Customer customer) {
        return modelMapper.map(customer, CustomerDTO.class);
    }

    public Customer mapToCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerDTO, Customer.class);
    }
}
