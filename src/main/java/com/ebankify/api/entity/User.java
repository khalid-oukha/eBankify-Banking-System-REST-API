package com.ebankify.api.entity;


import com.ebankify.api.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private int age;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<BankAccount> accounts;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Loan> loans;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Invoice> invoices;
}
