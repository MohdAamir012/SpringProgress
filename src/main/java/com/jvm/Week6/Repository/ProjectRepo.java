package com.jvm.Week6.Repository;

import com.jvm.Week6.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
}
