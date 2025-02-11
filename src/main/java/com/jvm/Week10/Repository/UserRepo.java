package com.jvm.Week10.Repository;


import com.jvm.Week10.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
    User findByName(String name);
}

