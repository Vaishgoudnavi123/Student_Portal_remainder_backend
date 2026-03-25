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

    // ✅ REGISTER FIX
  public User register(User user) {

    // 🔥 CHECK: is there any APPROVED admin?
    boolean approvedAdminExists = repo.findAll().stream()
            .anyMatch(u -> "ADMIN".equals(u.getRole()) && Boolean.TRUE.equals(u.getApproved()));

    if ("ADMIN".equals(user.getRole())) {

        if (!approvedAdminExists) {
            // ✅ FIRST APPROVED ADMIN
            user.setApproved(true);
        } else {
            user.setApproved(false);
        }

    } else {
        user.setApproved(true);
    }

    return repo.save(user);
}

    // ✅ LOGIN FIX
    public User login(String email, String password) {
        User user = repo.findByEmail(email);

        if (user == null)
            throw new RuntimeException("User not found");

        if (!user.getPassword().equals(password))
            throw new RuntimeException("Invalid password");

        // ⛔ BLOCK UNAPPROVED ADMIN
        if ("ADMIN".equals(user.getRole()) && !user.getApproved()) {
            throw new RuntimeException("Admin not approved yet");
        }

        return user;
    }

    // ✅ GET USERS
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    // ✅ APPROVE ADMIN
    public User approveAdmin(Long id) {
        User user = repo.findById(id).orElseThrow();
        user.setApproved(true);
        return repo.save(user);
    }
}