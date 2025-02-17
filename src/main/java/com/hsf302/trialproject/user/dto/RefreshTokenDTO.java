package com.hsf302.trialproject.user.dto;

import com.hsf302.trialproject.common.dto.BaseDTO;
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
