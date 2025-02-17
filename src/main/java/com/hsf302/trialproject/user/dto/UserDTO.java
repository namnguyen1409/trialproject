package com.hsf302.trialproject.user.dto;

import com.hsf302.trialproject.common.dto.BaseDTO;
import com.hsf302.trialproject.user.enums.RoleType;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO extends BaseDTO {
    private Long id;
    private String username;
    private String password;
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
}
