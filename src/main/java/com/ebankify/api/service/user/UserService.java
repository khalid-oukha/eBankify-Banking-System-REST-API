package com.ebankify.api.service.user;

import com.ebankify.api.dto.user.UserRequestDTO;
import com.ebankify.api.dto.user.UserResponseDTO;
import com.ebankify.api.entity.User;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.exception.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService{
    UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException;
    UserResponseDTO getUserById(Long userId) throws UserNotFoundException;
    UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) throws UserNotFoundException;
    void deleteUser(Long userId) throws UserNotFoundException;
    List<User> getAllUsers();
}
