package com.ebankify.api.web.dto.user;

import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.Role;
import jakarta.validation.constraints.*;

public record UserRequestDTO(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username,

        @NotBlank(message = "Email is required")
        @Email(message = "Email is invalid")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotNull(message = "Age is required")
        @Min(value = 18, message = "Age must be at least 18")
        @Max(value = 100, message = "Age must be at most 100")
        Integer age,

        @NotNull(message = "Role is required")
        Role role
) {
    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAge(age);
        user.setRole(role);
        return user;
    }
}