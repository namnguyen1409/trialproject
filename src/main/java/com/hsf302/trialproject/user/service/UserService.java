package com.hsf302.trialproject.user.service;

import com.hsf302.trialproject.user.dto.UserDTO;
import com.hsf302.trialproject.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<UserDTO> findPaginatedUsers(Pageable pageable);
    Page<UserDTO> findPaginatedUsersByUsername(String username, Pageable pageable);
    UserDTO findUserById(Long id);
    void saveUser(UserDTO userDTO);
    User findUserByUsername(String username);

}
