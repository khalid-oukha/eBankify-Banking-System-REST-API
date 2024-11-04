# eBankify Banking Management System

## Overview

eBankify is an online banking management system developed using Spring Boot. This application provides a RESTful API for managing banking operations, including user management, account management, and transaction processing. It supports multiple user roles (ADMIN, USER, EMPLOYEE) with defined permissions and access rights.

## Features

- **User Management**: Admins can create, modify, and delete users, while users can manage their own accounts.
- **Account Management**: Users can create and manage their bank accounts, view balances, and access transaction histories.
- **Transaction Processing**: Users can perform various types of transactions, including:
    - **Classic Transfers**: Standard transactions between accounts.
    - **Instant Transfers**: Transactions processed immediately.
    - **Scheduled Transfers**: Allow users to program transfers at defined intervals (daily, weekly, monthly).
- **Centralized Exception Handling**: Provides structured error messages in JSON format for consistent error management.
- **Database Migration**: Utilizes Liquibase for managing database migrations and versioning.
- **Testing**: Includes unit tests using JUnit and integration tests with Testcontainers for reliable code coverage.

## Technologies Used

- **Spring Boot**: Framework for building the RESTful API.
- **Spring Data JPA**: For database interactions and ORM capabilities.
- **PostgreSQL**: Database management system for persistent data storage.
- **Liquibase**: For managing database migrations.
- **JUnit**: For unit testing the application logic.
- **Testcontainers**: For integration testing with PostgreSQL in isolated environments.
- **Lombok**: To reduce boilerplate code with annotations for getters, setters, and builders.
- **MapStruct**: For converting between entities and DTOs (Data Transfer Objects).

## Getting Started

### Prerequisites

Before you begin, ensure you have met the following requirements:

- Java 17
- Maven
- PostgreSQL

### Installation

1. Clone the repository:
   ```bash
   https://github.com/khalid-oukha/eBankify-Banking-System-REST-API.git
   ````
