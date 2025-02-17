package com.hsf302.trialproject.user.dto;

import com.hsf302.trialproject.common.dto.BaseDTO;
import com.hsf302.trialproject.inventory.dto.InventoryDTO;
import com.hsf302.trialproject.product.entity.Product;
import com.hsf302.trialproject.user.enums.RoleType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsDTO extends BaseDTO {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Boolean gender;
    private LocalDate birthday;
    private String address;
    private RoleType role;
    private boolean isLocked;
    private String lockReason;
    private LocalDateTime createdAt;
    private String avatar;
    private Set<InventoryDTO> inventories;
    private InventoryDTO assignedInventory;
}
