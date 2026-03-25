package com.example.backend.service;

import com.example.backend.model.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    // ✅ Register a new user
    public User register(User user) {
        // Check if any approved admin already exists
        boolean approvedAdminExists = repo.findAll().stream()
                .anyMatch(u -> "ADMIN".equals(u.getRole()) && Boolean.TRUE.equals(u.getApproved()));

        if ("ADMIN".equals(user.getRole())) {
            if (!approvedAdminExists) {
                // First admin → auto-approved
                user.setApproved(true);
            } else {
                // Later admins → require manual approval
                user.setApproved(false);
            }
        } else {
            // Non-admin users are always approved
            user.setApproved(true);
        }

        return repo.save(user);
    }

    // ✅ Login
    public User login(String email, String password) {
        User user = repo.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }

        // Block unapproved admins, except the first one
        if ("ADMIN".equals(user.getRole())) {
            boolean approvedAdminExists = repo.findAll().stream()
                    .anyMatch(u -> "ADMIN".equals(u.getRole()) && Boolean.TRUE.equals(u.getApproved()));

            if (!user.getApproved() && approvedAdminExists) {
                throw new RuntimeException("Admin not approved yet");
            }
        }

        return user;
    }

    // ✅ Get all users
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ✅ Approve an admin manually
    public User approveAdmin(Long id) {
        User user = repo.findById(id).orElseThrow();
        user.setApproved(true);
        return repo.save(user);
    }
}
