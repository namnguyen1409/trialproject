package com.hsf302.trialproject.user.mapper;

import com.hsf302.trialproject.user.dto.RefreshTokenDTO;
import com.hsf302.trialproject.user.entity.RefreshToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenMapper {
    ModelMapper modelMapper = new ModelMapper();

    public RefreshTokenDTO mapToRefreshTokenDTO(RefreshToken refreshToken) {
        return RefreshTokenDTO.builder()
                .id(refreshToken.getId())
                .token(refreshToken.getToken())
                .userId(refreshToken.getUser().getId())
                .expiryDate(refreshToken.getExpiryDate())
                .build();
    }

    public RefreshToken mapToRefreshToken(RefreshTokenDTO refreshTokenDTO) {
        return modelMapper.map(refreshTokenDTO, RefreshToken.class);
    }

}
