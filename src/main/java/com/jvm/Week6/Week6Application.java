package com.jvm.Week6;
/*
Week 6: Hibernate and Advanced JPA
Objective: Understand Hibernate and advanced JPA features.
Focus:
Hibernate: Session management, lazy loading to eage, caching level 2 ,
and Hibernate Query Language (HQL) all crud. difference bw all
Advanced JPA: Criteria API, JPQL, and batch processing.
Project: Integrate Hibernate features into the existing
Spring Boot application for complex queries and optimize performance.
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Week6Application {
    public static void main(String[] args) {

        SpringApplication.run(Week6Application.class, args);
        System.out.println("Hellooo sir it is week6 Application!");
    }
}
