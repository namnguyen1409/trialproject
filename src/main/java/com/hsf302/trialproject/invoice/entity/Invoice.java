package com.hsf302.trialproject.invoice.entity;

import com.hsf302.trialproject.common.entity.BaseEntity;
import com.hsf302.trialproject.customer.entity.Customer;
import com.hsf302.trialproject.invoice.enums.InvoiceType;
import com.hsf302.trialproject.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice extends BaseEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, columnDefinition = "nvarchar(10)")
    private InvoiceType type;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "total_discount", nullable = false)
    private BigDecimal totalDiscount;

    @Column(name="total_payable", nullable = false)
    private BigDecimal totalPayable;

    @Column(name = "total_paid", nullable = false)
    private BigDecimal totalPaid;

    @Column(name = "total_debt", nullable = false)
    private BigDecimal totalDebt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "description", columnDefinition = "nvarchar(255)")
    private String description;

    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;

    @Column(name = "is_shipped", nullable = false)
    private boolean isShipped;

}
