package org.nastya.controller;

import org.nastya.dto.UserDTO;
import org.nastya.service.exception.UserAlreadyExistsException;
import org.nastya.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        log.info("Received request to get user by username: {}", username);
        Optional<UserDTO> userDTO = userService.findByUsername(username);
        if (userDTO.isPresent()) {
            log.info("User found: {}", userDTO.get());
            return ResponseEntity.ok(userDTO.get());
        } else {
            log.warn("User not found: {}", username);
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        log.info("Received request to create user: {}", userDTO);
        try {
            UserDTO savedUser = userService.saveUser(userDTO);
            log.info("User created successfully: {}", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (UserAlreadyExistsException e) {
            log.warn("User creation failed: username already exists: {}", userDTO.getUsername(), e);
            throw new RuntimeException("User already exists", e);
        } catch (IllegalArgumentException e) {
            log.warn("User creation failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        log.info("Received request to delete user with id: {}", id);
        userService.deleteUser(id);
        log.info("User with id {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
