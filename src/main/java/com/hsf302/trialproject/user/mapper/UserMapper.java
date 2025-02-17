package com.hsf302.trialproject.user.mapper;

import com.hsf302.trialproject.user.dto.UserDTO;
import com.hsf302.trialproject.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    ModelMapper modelMapper = new ModelMapper();

    public UserDTO mapToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public User mapToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }
}
