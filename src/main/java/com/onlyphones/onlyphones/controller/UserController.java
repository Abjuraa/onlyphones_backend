package com.onlyphones.onlyphones.controller;

import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")

public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<?> getAllUsers() {
        List<User> response = userService.getAllUsers();

        if (response.isEmpty()) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(String id) {
        User response = userService.getUserById(id);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        User response = userService.createUser(newUser);

        if (response == null) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/edituser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User newData) {
        User response = userService.updateUser(id, newData);

        if (response == null) {
             return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean response = userService.deleteUser(id);

        if (response) {
            return ResponseEntity.ok(id);
        }

        return ResponseEntity.notFound().build();
    }
}
