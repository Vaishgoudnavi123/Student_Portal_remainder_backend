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

    public Notification add(Notification n) {
        return repo.save(n);
    }

    public List<Notification> getAll() {
        return repo.findAll();
    }

    public boolean delete(Long id) {
        Optional<Notification> n = repo.findById(id);
        if (n.isPresent()) {
            repo.delete(n.get());
            return true;
        }
        return false;
    }

    // ✅ MARK AS DONE
    public boolean markAsDone(Long id) {
        Optional<Notification> optional = repo.findById(id);

        if (optional.isPresent()) {
            Notification n = optional.get();
            n.setCompleted(true);
            repo.save(n);
            return true;
        }
        return false;
    }
}