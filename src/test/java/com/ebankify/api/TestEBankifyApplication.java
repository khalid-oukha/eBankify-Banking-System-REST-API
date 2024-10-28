package com.ebankify.api;

import org.springframework.boot.SpringApplication;

public class TestEBankifyApplication {

    public static void main(String[] args) {
        SpringApplication.from(EBankifyApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
