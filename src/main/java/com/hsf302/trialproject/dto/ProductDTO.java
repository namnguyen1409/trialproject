package com.hsf302.trialproject.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO extends BaseDTO {
    @NotBlank(message = "Tên gạo không được để trống")
    @Size(min = 3, max = 50, message = "Tên gạo phải có từ 3 đến 50 ký tự")
    @Pattern(regexp = "^[^!@#$%^&*()_+=\\[\\]{}|,;:'\"<>?/\\\\~`]*$", message = "Tên kho gạo được chứa ký tự đặc biệt")
    private String name;
    @NotNull(message = "Giá không được để trống")
    private BigDecimal price;
    @NotBlank(message = "Mô tả không được để trống")
    private String description;
    @NotBlank(message = "Hình ảnh không được để trống")
    private String image;
    private String descriptionPlainText;
}
