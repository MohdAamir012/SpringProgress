package com.jvm.Week10.Controller;

import com.jvm.Week10.Entity.User;
import com.jvm.Week10.Exception.UserResponse;
import com.jvm.Week10.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    AuthenticationManager authManager;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserService.UserRecord>> getAllUsers() {
            List<UserService.UserRecord> UserList = userService.getAllUsers()
                    .stream()
                    .map(emp -> new UserService.UserRecord(emp.getName(),emp.getPassword(),emp.getEmail(), emp.getMobile(),emp.getAddress(), emp.getRoles()))
                    .toList();
            return ResponseEntity.ok(UserList);
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
        try {
            UserService.UserRecord user = userService.getUserById(id);
            return ResponseEntity.ok(new UserResponse.Success<>(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UserResponse.Error("User not found", 404));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserService.UserRecord record) {
            var user = new User();
            user.setName(record.name());
            user.setPassword(record.password());
        user.setEmail(record.email());
        user.setMobile(record.mobile());
        user.setAddress(record.address());
        user.setRoles(record.roles());
            UserService.UserRecord savedUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new UserResponse.Success<>( savedUser));
    }

    @PostMapping("/login")
    public String login (@RequestBody UserService.UserRecord record){

        return userService.verify(record);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserService.UserRecord record) {
        try {
            var user = new User();
            user.setName(record.name());
            user.setEmail(record.email());
            user.setMobile(record.mobile());
            user.setAddress(record.address());
            UserService.UserRecord updatedUser = userService.updateUser(id, user);
            return ResponseEntity.ok(new UserResponse.Success<>(updatedUser));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UserResponse.Error("Failed to update User", 404));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new UserResponse.Success<>(null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new UserResponse.Error("Failed to delete User", 404));
        }
    }
}
