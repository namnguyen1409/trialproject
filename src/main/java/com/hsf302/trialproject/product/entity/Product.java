package com.hsf302.trialproject.product.entity;

import com.hsf302.trialproject.common.entity.BaseEntity;
import com.hsf302.trialproject.inventory.entity.Zone;
import com.hsf302.trialproject.invoice.entity.InvoiceItem;
import com.hsf302.trialproject.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * tên, mô tả, ảnh, giá
 */

@Entity
@Data
@SuperBuilder(toBuilder = true)
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(50)")
    private String name;

    @Column(name = "price", nullable = false, columnDefinition = "decimal(18,0)")
    private BigDecimal price;

    @Column(name = "description", nullable = true, columnDefinition = "nvarchar(MAX)")
    private String description;

    @Column(name = "description_plain_text", nullable = true, columnDefinition = "nvarchar(MAX)")
    private String descriptionPlainText;

    @Column(name = "image", nullable = true, columnDefinition = "nvarchar(255)")
    private String image;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Zone> zones = new HashSet<>();

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<InvoiceItem> invoiceItems = new HashSet<>();
}
