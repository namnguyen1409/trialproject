package com.hsf302.trialproject.customer.controller;

import com.hsf302.trialproject.customer.dto.CustomerDTO;
import com.hsf302.trialproject.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/customer")
@AllArgsConstructor
public class CustomerRestController {
    private final CustomerService customerService;

    @GetMapping("/search")
    public ResponseEntity<CustomerDTO> searchCustomers(
            @RequestParam(value = "phoneNumber") String phone
    ) {
        var customer = customerService.findCustomerByPhone(phone);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

}
