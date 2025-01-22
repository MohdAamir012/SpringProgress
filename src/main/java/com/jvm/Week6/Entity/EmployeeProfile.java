package com.jvm.Week6.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;
import org.hibernate.annotations.Cache;

@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ProfileID", referencedColumnName = "ProfileID")
    private List<Address> address;

}
