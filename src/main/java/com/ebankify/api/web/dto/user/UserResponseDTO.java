package com.ebankify.api.web.dto.user;


import com.ebankify.api.entity.User;
import com.ebankify.api.entity.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private int age;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static UserResponseDTO fromUser(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .age(user.getAge())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public User toUser() {
        return User.builder()
                .id(this.id)
                .username(this.username)
                .email(this.email)
                .age(this.age)
                .role(this.role)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
