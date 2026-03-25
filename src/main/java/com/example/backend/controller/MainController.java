package com.example.backend.controller;

import com.example.backend.model.User;
import com.example.backend.model.Notification;
import com.example.backend.service.UserService;
import com.example.backend.service.NotificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    // REGISTER
    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.register(user);
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User u = userService.login(user.getEmail(), user.getPassword());
            return ResponseEntity.ok(u);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ADD NOTIFICATION
    @PostMapping("/notifications")
    public Notification add(@RequestBody Notification n) {
        return notificationService.add(n);
    }

    // GET ALL
    @GetMapping("/notifications")
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    // DELETE
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean deleted = notificationService.delete(id);
        if (deleted) {
            return ResponseEntity.ok("Deleted");
        } else {
            return ResponseEntity.status(404).body("Not found");
        }
    }

    // ✅ MARK AS DONE
    @PutMapping("/notifications/{id}/complete")
    public ResponseEntity<?> markAsDone(@PathVariable Long id) {
        boolean updated = notificationService.markAsDone(id);
        if (updated) {
            return ResponseEntity.ok("Marked as done");
        } else {
            return ResponseEntity.status(404).body("Notification not found");
        }
    }
}