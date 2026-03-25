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
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    // USER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        return userService.login(user.getEmail(), user.getPassword());
    }

    // NOTIFICATIONS
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

    // ✅ COMPLETE
    @PutMapping("/notifications/complete/{nid}/{uid}")
    public Notification complete(@PathVariable Long nid, @PathVariable Long uid) {
        return notificationService.markComplete(nid, uid);
    }
}