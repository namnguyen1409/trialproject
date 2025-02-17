package com.hsf302.trialproject.user.service.Impl;

import com.hsf302.trialproject.user.dto.UserDTO;
import com.hsf302.trialproject.user.entity.User;
import com.hsf302.trialproject.user.exception.UserNoSuchElementException;
import com.hsf302.trialproject.user.mapper.UserMapper;
import com.hsf302.trialproject.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    public UserServiceImplTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findUserById_ShouldReturnUserDTO_WhenUserExists() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        UserDTO mockUserDTO = new UserDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userMapper.mapToUserDTO(mockUser)).thenReturn(mockUserDTO);

        UserDTO result = userServiceImpl.findUserById(userId);

        assertNotNull(result);
        assertEquals(mockUserDTO, result);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, times(1)).mapToUserDTO(mockUser);
    }

    @Test
    void findUserById_ShouldReturnNull_WhenUserDoesNotExist() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        UserDTO result = userServiceImpl.findUserById(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
        verify(userMapper, never()).mapToUserDTO(any(User.class));
    }

    @Test
    void findUserById_ShouldThrowUserNoSuchElementException_WhenErrorOccurs() {
        Long userId = 1L;
        String errorMessage = "User not found";

        when(userRepository.findById(userId)).thenThrow(new RuntimeException("Database error"));
        when(messageSource.getMessage("entity.notfound", new Object[]{userId}, Locale.getDefault())).thenReturn(errorMessage);

        UserNoSuchElementException exception = assertThrows(UserNoSuchElementException.class, () -> userServiceImpl.findUserById(userId));

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(userId, exception.getId());
        verify(userRepository, times(1)).findById(userId);
        verify(messageSource, times(1)).getMessage("entity.notfound", new Object[]{userId}, Locale.getDefault());
    }
}