package com.jvm.Week5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EmpID")
    private Integer id;
    private String designation;
    private Double salary;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="ProfileID")
    private EmployeeProfile employeeProfile;

    @ManyToMany
    private Set<Project> assignedProjects =new HashSet<>();

}
