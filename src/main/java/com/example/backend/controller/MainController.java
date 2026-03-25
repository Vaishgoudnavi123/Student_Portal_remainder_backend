package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.model.Notification;
import com.example.backend.service.UserService;
import com.example.backend.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

   // ✅ GET USERS
@GetMapping("/users")
public List<User> getUsers() {
    return userService.getAllUsers();
}

// ✅ APPROVE ADMIN
@PutMapping("/users/approve/{id}")
public User approveAdmin(@PathVariable Long id) {
    return userService.approveAdmin(id);
}

    @PostMapping("/notifications")
    public Notification add(@RequestBody Notification n) {
        return notificationService.add(n);
    }

    @GetMapping("/notifications")
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    @DeleteMapping("/notifications/{id}")
    public void delete(@PathVariable Long id) {
        notificationService.delete(id);
    }

    @PutMapping("/notifications/complete/{id}/{userId}")
    public Notification complete(@PathVariable Long id, @PathVariable Long userId) {
        return notificationService.markComplete(id, userId);
    }
    
}