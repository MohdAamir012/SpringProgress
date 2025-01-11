package com.jvm.Week5.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Project")
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Integer projectId;
    String projectName;
    String BU;

    @JsonIgnore
    @ManyToMany(mappedBy = "assignedProjects")
    private Set<Employee>employeeList=new HashSet<>();
}
