package com.example.backend.service;

import com.example.backend.model.Notification;
import com.example.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repo;

    public Notification add(Notification n) {
        return repo.save(n);
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Notification markComplete(Long id, Long userId) {
        Notification n = repo.findById(id).orElseThrow();

        if (!n.getCompletedUsers().contains(userId)) {
            n.getCompletedUsers().add(userId);
        }

        return repo.save(n);
    }
}