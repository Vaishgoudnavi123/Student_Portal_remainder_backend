package com.example.backend.service;

import com.example.backend.model.Notification;
import com.example.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repo;

    // Add notification
    public Notification add(Notification n) {
        return repo.save(n);
    }

    // Get all notifications
    public List<Notification> getAll() {
        return repo.findAll();
    }

    // Delete notification
    public boolean delete(Long id) {
        Optional<Notification> n = repo.findById(id);
        if (n.isPresent()) {
            System.out.println("Deleting notification id: " + id);
            repo.delete(n.get());
            return true;
        } else {
            System.out.println("Notification not found id: " + id);
        }
        return false;
    }
}