package com.jvm.Week5;
/*
Week 5: Introduction to Spring Data JPA
Objective: Learn how to work with relational databases using Spring Data JPA.
Focus:

JPA basics: entity mapping, CRUD operations, and query methods.
Relationships: One-to-One, One-to-Many, and Many-to-Many mappings.
Project: Implement database operations in the Spring Boot application using Spring Data JPA.
 */

//JPQl java persistence query language it is based on sql syntax using JPA repo
// Native SQL query
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week5Application {
    public static void main(String[] args) {

        SpringApplication.run(Week5Application.class, args);
        System.out.println("Hellooo sir it is week5 Application!");
    }

}
