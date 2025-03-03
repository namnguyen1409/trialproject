package com.hsf302.trialproject.mapper;

import com.hsf302.trialproject.dto.InvoiceDTO;
import com.hsf302.trialproject.entity.Invoice;
import com.hsf302.trialproject.enums.InvoiceType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InvoiceMapper {
    ModelMapper modelMapper = new ModelMapper();

    public InvoiceDTO mapToInvoiceDTO(Invoice invoice) {
        return InvoiceDTO.builder()
                .id(invoice.getId())
                .type(invoice.getType().toString())
                .totalPrice(invoice.getTotalPrice())
                .totalDiscount(invoice.getTotalDiscount())
                .totalPaid(invoice.getTotalPaid())
                .totalDebt(invoice.getTotalDebt())
                .customerId(invoice.getCustomer().getId())
                .description(invoice.getDescription())
                .isShipped(invoice.isShipped())
                .build();
    }

    public Invoice mapToInvoice(InvoiceDTO invoiceDTO) {
        return Invoice.builder()
                .id(invoiceDTO.getId())
                .type(InvoiceType.valueOf(invoiceDTO.getType()))
                .totalPrice(invoiceDTO.getTotalPrice())
                .totalDiscount(invoiceDTO.getTotalDiscount())
                .totalPaid(invoiceDTO.getTotalPaid())
                .totalDebt(invoiceDTO.getTotalDebt())
                .description(invoiceDTO.getDescription())
                .isShipped(invoiceDTO.isShipped())
                .build();

    }
}
