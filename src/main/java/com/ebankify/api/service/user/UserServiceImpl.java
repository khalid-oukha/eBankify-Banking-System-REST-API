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

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<UserResponseDTO> CreateUser(UserRequestDTO userRequestDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(userRequestDTO.email())) {
            throw new UserAlreadyExistsException("User with email " + userRequestDTO.email() + " already exists");
        }

        User savedUser = userRepository.save(userRequestDTO.toUser());

        UserResponseDTO responseDTO = UserResponseDTO.fromUser(savedUser);

        return ApiResponse.created("/users/" + savedUser.getId(), responseDTO);
    }


    @Override
    public ResponseEntity<UserResponseDTO> GetUserById(Long userId) throws UserNotFoundException {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDTO> UpdateUser(Long userId, UserRequestDTO userRequestDTO) throws UserNotFoundException {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDTO> DeleteUser(Long userId) throws UserNotFoundException {
        return null;
    }
}
