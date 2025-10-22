package com.onlyphones.onlyphones.controller.admin;

import com.onlyphones.onlyphones.entity.User;
import com.onlyphones.onlyphones.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")

public class UserController {

    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<?> getAllUsers() {
        List<User> response = userService.getAllUsers();

        if (response.isEmpty()) {
           return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(String id) {
        User response = userService.getUserById(id);

        if (response == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(response);
    }

    @PutMapping("/edituser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User newData) {
        User response = userService.updateUser(id, newData);

        if (response == null) {
             return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
      try {
          userService.deleteUser(id);
          return ResponseEntity.ok("Usuario eliminado correctamente");
      } catch (Exception e) {
          return ResponseEntity.status(500).body("No se pudo eliminar el usuario" + e.getMessage());
      }
    }
}
