package com.jvm.Week10.Service;

import com.jvm.Week10.Entity.User;
import com.jvm.Week10.Exception.ResourceNotFoundException;
import com.jvm.Week10.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String verify(UserRecord record) {
        Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(record.name(),record.password()));
        if (authentication.isAuthenticated())
            return jwtService.generateToken(record.name);
        return "false";
    }

    // Use records for lightweight DTOs
    public record UserRecord(String name,String password, String email,String mobile, String address,String roles) {}

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public UserRecord getUserById(int id) {
            User user = userRepo.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("""
                        User not found with ID: %d, Please ensure the ID is correct.""".formatted(id), id));
            return new UserRecord(user.getName(),user.getPassword(), user.getEmail(),user.getMobile(), user.getAddress(),user.getRoles());
    }

    public UserRecord addUser(User e) {
        try {
            e.setPassword(encoder.encode(e.getPassword()));
            User savedUser = userRepo.save(e);
            return new UserRecord(savedUser.getName(),savedUser.getPassword(),savedUser.getEmail(),savedUser.getMobile(), savedUser.getAddress(), savedUser.getRoles());
        } catch (Exception e1) {
            throw new RuntimeException("Error while adding User", e1);
        }
    }

    public UserRecord updateUser(int id, User emp) {
            User updatedUser = userRepo.findById(id)
                    .map(existingUser -> {
                        existingUser.setName(emp.getName());
                        existingUser.setPassword(encoder.encode(emp.getPassword()));
                        existingUser.setEmail(emp.getEmail());
                        existingUser.setMobile(emp.getMobile());
                        existingUser.setAddress(emp.getAddress());
                        return userRepo.save(existingUser);
                    })
                    .orElseThrow(() -> new ResourceNotFoundException("""
                        Unable to update. User with ID: %d not found.""".formatted(id), id));
            return new UserRecord(updatedUser.getName(),updatedUser.getPassword(),updatedUser.getEmail(),updatedUser.getMobile(), updatedUser.getAddress(), updatedUser.getRoles());
    }


    public void deleteUser(int id) {
            if (userRepo.existsById(id)) {
                userRepo.deleteById(id);
            } else {
                throw new ResourceNotFoundException("User not found with ID: ", id);
            }
    }
}
