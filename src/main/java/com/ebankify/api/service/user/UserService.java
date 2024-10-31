package com.ebankify.api.service.user;

import com.ebankify.api.dto.user.UserRequestDTO;
import com.ebankify.api.dto.user.UserResponseDTO;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.exception.user.UserNotFoundException;
import org.springframework.http.ResponseEntity;

public interface UserService{
    ResponseEntity<UserResponseDTO> CreateUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException;
    ResponseEntity<UserResponseDTO> GetUserById(Long userId) throws UserNotFoundException;
    ResponseEntity<UserResponseDTO> UpdateUser(Long userId, UserRequestDTO userRequestDTO) throws UserNotFoundException;
    ResponseEntity<UserResponseDTO> DeleteUser(Long userId) throws UserNotFoundException;
}
