package com.ebankify.api.dto.bankAccount;

import com.ebankify.api.enums.Role;
import jakarta.validation.constraints.*;

public record UserBankAccountRequestDTO(
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
        Role role,

        @Min(value = 0, message = "Balance must be at least 0")
        double balance
) {
}
