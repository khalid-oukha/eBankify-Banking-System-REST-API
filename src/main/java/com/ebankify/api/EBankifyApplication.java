package com.ebankify.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class EBankifyApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankifyApplication.class, args);
    }

}
