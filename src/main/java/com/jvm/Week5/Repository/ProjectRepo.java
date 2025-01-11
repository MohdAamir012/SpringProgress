package com.jvm.Week5.Repository;

import com.jvm.Week5.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepo extends JpaRepository<Project,Integer> {
}
