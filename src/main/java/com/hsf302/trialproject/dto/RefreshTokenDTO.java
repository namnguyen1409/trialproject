package com.hsf302.trialproject.dto;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenDTO extends BaseDTO {
    private Long id;
    private String token;
    private Long userId;
    private LocalDateTime expiryDate;
}
