package com.ebankify.api.util;

import com.ebankify.api.entity.User;
import com.ebankify.api.exception.user.UserNotFoundException;
import com.ebankify.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

    private static UserRepository userRepository;

    @Autowired
    public UserUtils(UserRepository userRepository) {
        UserUtils.userRepository = userRepository;
    }

    public static User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            return userRepository.findByEmail(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
        }
        throw new RuntimeException("User is not authenticated");
    }

    public static void validateUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User with id " + userId + " not found");
        }
    }
}