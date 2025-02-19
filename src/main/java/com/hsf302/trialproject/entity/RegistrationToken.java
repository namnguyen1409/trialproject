package com.hsf302.trialproject.entity;

import com.hsf302.trialproject.enums.RoleType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Table(name = "registration_tokens")
public class RegistrationToken extends BaseEntity {
    private String email;
    private String token;
    @Enumerated(EnumType.STRING)
    private RoleType role;
}
