package com.hsf302.trialproject.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceItemDTO {
    private Long id;
    private Long invoiceId;
    private Long productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal discount;
}
