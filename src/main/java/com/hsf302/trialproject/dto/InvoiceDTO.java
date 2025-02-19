package com.hsf302.trialproject.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
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

    public InvoiceDTO(Long id, String type, BigDecimal totalPrice, BigDecimal totalDiscount, BigDecimal totalPaid, BigDecimal totalDebt, Long customerId, String description, LocalDate invoiceDate, boolean isShipped) {
        this.id = id;
        this.type = type;
        this.totalPrice = totalPrice;
        this.totalDiscount = totalDiscount;
        this.totalPaid = totalPaid;
        this.totalDebt = totalDebt;
        this.customerId = customerId;
        this.description = description;
        this.invoiceDate = invoiceDate;
        this.isShipped = isShipped;
    }
}
