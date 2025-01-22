package com.jvm.Week6.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ProfileID")
    private EmployeeProfile employeeProfile;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Project> assignedProjects = new HashSet<>();

}
