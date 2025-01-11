package com.jvm.Week5.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "EmployeeProfile")
@Getter
@Setter
@NoArgsConstructor
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProfileID")
    Integer id;
    String name;
    String email;
    String mobile;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "ProfileID",referencedColumnName = "ProfileID")
    private List<Address> address;

}
