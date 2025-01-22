package com.jvm.Week6.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
