package com.hsf302.trialproject.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hsf302.trialproject.dto.ProductDTO;
import com.hsf302.trialproject.exception.Http500;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "invoice_items")
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount", nullable = false)
    private BigDecimal discount;

    @Column(name="payable", nullable = false)
    private BigDecimal payable;

    // product snapshot
    @Column(name = "product_snapshot", nullable = false, columnDefinition = "nvarchar(max)")
    private String productSnapshot;

    public void setProductSnapshot(ProductDTO productDTO) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            this.productSnapshot = objectMapper.writeValueAsString(productDTO);
        } catch (JsonProcessingException e) {
            throw new Http500("Error when serializing product snapshot");
        }
    }

    public ProductDTO getProductSnapshot() {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(this.productSnapshot, ProductDTO.class);
        } catch (JsonProcessingException e) {
            throw new Http500("Error when deserializing product snapshot");
        }
    }

}