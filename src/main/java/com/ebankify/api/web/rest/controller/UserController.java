package com.ebankify.api.web.rest.controller;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.entity.User;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.exception.user.UserNotFoundException;
import com.ebankify.api.service.user.UserService;
import com.ebankify.api.web.dto.user.UserRequestDTO;
import com.ebankify.api.web.dto.user.UserResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO createdUser = userService.createUser(userRequestDTO);
            return ApiResponse.created("/users/" + createdUser.getId(), createdUser);
        } catch (UserAlreadyExistsException e) {
            return ApiResponse.badRequest("User with email " + userRequestDTO.email() + " already exists");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ApiResponse.ok(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@Valid @PathVariable Long userId) {
        try {
            UserResponseDTO user = userService.getUserById(userId);
            return ApiResponse.ok(user);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserResponseDTO> updateUser(@Valid @PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO updatedUser = userService.updateUser(userId, userRequestDTO);
            return ApiResponse.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@Valid @PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ApiResponse.noContent();
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getAuthenticatedUser(Authentication authentication) {
        String username = authentication.getName();
        return ApiResponse.ok(UserResponseDTO.fromUser(userService.findByEmail(username)));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@Valid @PathVariable String email) {
        try {
            User user = userService.findByEmail(email);
            UserResponseDTO userResponseDTO = UserResponseDTO.fromUser(user);
            return ApiResponse.ok(userResponseDTO);
        } catch (UserNotFoundException e) {
            return ApiResponse.notFound();
        }
    }
}
