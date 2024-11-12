package com.ebankify.api.ServicesTests;

import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.Role;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.exception.user.UserNotFoundException;
import com.ebankify.api.repository.UserRepository;
import com.ebankify.api.service.user.UserServiceImpl;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import com.ebankify.api.web.dto.user.UserResponseDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTests {

    private UserRepository userRepository;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void createUser_shouldReturnCreatedUser() throws UserAlreadyExistsException {

        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "khalidoukha",
                "khalid@example.com",
                "password123",
                25,
                Role.USER
        );

        User savedUser = userRequestDTO.toUser();

        when(userRepository.existsByEmail(userRequestDTO.email())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserResponseDTO result = userService.createUser(userRequestDTO);

        assertEquals(result, UserResponseDTO.fromUser(savedUser), "The result should match the saved user");

        verify(userRepository, times(1)).save(any(User.class));
        verify(userRepository, times(1)).existsByEmail(userRequestDTO.email());
    }


    @Test
    void createUser_should_throw_UserAlreadyExistsException_if_email_exists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "khalidoukha",
                "khalid@example.com",
                "password123",
                25,
                Role.USER
        );

        when(userRepository.existsByEmail(userRequestDTO.email())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(userRequestDTO.toUser());

        UserAlreadyExistsException exception = assertThrows(
                UserAlreadyExistsException.class,
                () -> userService.createUser(userRequestDTO)
        );

        assertEquals("User with email " + userRequestDTO.email() + " already exists", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void getUserById_should_return_user_if_exists() throws UserNotFoundException {
        User user = User.builder()
                .id(1L)
                .username("oukhakhalid")
                .email("oukhakhalid@gmail.com")
                .password("password123")
                .age(25)
                .role(Role.USER)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO result = userService.getUserById(1L);
        assertEquals(result, UserResponseDTO.fromUser(user), "The result should match the user");
    }

    @Test
    void getUserById_should_throw_UserNotFoundException_if_user_not_found() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUserById(1L)
        );

        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void updateUser_should_update_user_if_exists() throws UserNotFoundException {
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                "khalidoukha",
                "khalid@example.com",
                "password123",
                25,
                Role.USER
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(userRequestDTO.toUser()));
        when(userRepository.save(any(User.class))).thenReturn(userRequestDTO.toUser());

        UserResponseDTO result = userService.updateUser(1L, userRequestDTO);
        User updatedUser = userRequestDTO.toUser();

        assertEquals(result, UserResponseDTO.fromUser(updatedUser), "The result should match the updated user");

    }

    @Test
    void deleteUser_should_delete_user_if_exists() throws UserNotFoundException {
        Long userId = 1L;
        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().id(userId).build()));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).delete(any(User.class));
    }

    @Test
    void deleteUser_should_throw_UserNotFoundException_if_user_not_found() {

    }
}
