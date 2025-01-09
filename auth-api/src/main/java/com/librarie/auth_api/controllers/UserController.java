package com.librarie.auth_api.controllers;

import com.librarie.auth_api.dtos.SignupResponseDto;
import com.librarie.auth_api.dtos.UpdateUserDto;
import com.librarie.auth_api.entities.User;
import com.librarie.auth_api.entities.EnumRole;
import com.librarie.auth_api.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/api/users")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> authenticatedUser(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User currentUser = (User) authentication.getPrincipal();

        if (!currentUser.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(userOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<User>> allUsers() {
        List<User> users = userService.allUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UpdateUserDto dto) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOptional.get();
        user = userService.updateUserFromDto(dto, user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/add-role")
    public ResponseEntity<User> addRoleToUser(@PathVariable("id") Long id, @RequestParam EnumRole role) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOptional.get();
        user = userService.addRoleToUser(user, role);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}/remove-role")
    public ResponseEntity<User> removeRoleFromUser(@PathVariable("id") Long id, @RequestParam EnumRole role) {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        User user = userOptional.get();
        user = userService.removeRoleFromUser(user, role);
        return ResponseEntity.ok(user);
    }
}
