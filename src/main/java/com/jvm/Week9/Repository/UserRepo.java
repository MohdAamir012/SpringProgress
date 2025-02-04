package com.jvm.Week9.Repository;


import com.jvm.Week9.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer> {
 User findByName(String name);
}
