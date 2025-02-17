package com.hsf302.trialproject.invoice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {
    private Long id;
    private String type;
    private BigDecimal totalPrice;
    private BigDecimal totalDiscount;
    private BigDecimal totalPaid;
    private BigDecimal totalDebt;
    private Long customerId;
    private String description;
    private LocalDate invoiceDate;
    private boolean isShipped;
}
