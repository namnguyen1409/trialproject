package com.hsf302.trialproject.user.service.Impl;

import com.hsf302.trialproject.user.dto.UserDTO;
import com.hsf302.trialproject.user.entity.User;
import com.hsf302.trialproject.user.exception.UserNoSuchElementException;
import com.hsf302.trialproject.user.mapper.UserMapper;
import com.hsf302.trialproject.user.repository.UserRepository;
import com.hsf302.trialproject.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private MessageSource messageSource;

    @Override
    public Page<UserDTO> findPaginatedUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);
        return page.map(userMapper::mapToUserDTO);
    }

    @Override
    public Page<UserDTO> findPaginatedUsersByUsername(String username, Pageable pageable) {
        Page<User> page = userRepository.findByUsernameContaining(username, pageable);
        return page.map(userMapper::mapToUserDTO);
    }

    @Override
    public UserDTO findUserById(Long id) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            return userOptional.map(user -> userMapper.mapToUserDTO(user)).orElse(null);
        } catch (Exception exception) {
            String message = messageSource.getMessage("entity.notfound", new Object[]{id}, Locale.getDefault());
            throw new UserNoSuchElementException(message, id);
        }
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = userMapper.mapToUser(userDTO);
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
