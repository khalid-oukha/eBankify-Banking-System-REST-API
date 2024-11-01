package com.ebankify.api.service.user;

import com.ebankify.api.commons.ApiResponse;
import com.ebankify.api.dto.user.UserRequestDTO;
import com.ebankify.api.dto.user.UserResponseDTO;
import com.ebankify.api.entity.User;
import com.ebankify.api.exception.user.UserAlreadyExistsException;
import com.ebankify.api.exception.user.UserNotFoundException;
import com.ebankify.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userRequestDTO.email())) {
            throw new UserAlreadyExistsException("User with email " + userRequestDTO.email() + " already exists");
        }

        User savedUser = userRepository.save(userRequestDTO.toUser());
        return UserResponseDTO.fromUser(savedUser);
    }

    @Override
    public UserResponseDTO getUserById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        return UserResponseDTO.fromUser(user);
    }

    @Override
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));

        user.setUsername(userRequestDTO.username());
        user.setEmail(userRequestDTO.email());
        user.setPassword(userRequestDTO.password());
        user.setAge(userRequestDTO.age());
        user.setRole(userRequestDTO.role());

        User updatedUser = userRepository.save(user);
        return UserResponseDTO.fromUser(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        userRepository.delete(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}