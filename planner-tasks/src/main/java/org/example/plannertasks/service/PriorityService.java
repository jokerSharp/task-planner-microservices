package org.example.plannertasks.service;

import lombok.AllArgsConstructor;
import org.example.plannertasks.repository.PriorityRepository;
import org.example.taskplannerentity.entity.Priority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PriorityService {

    private final PriorityRepository repository;

    public List<Priority> findAll(Long userId) {
        return repository.findByUserIdOrderByIdAsc(userId);
    }

    @Transactional
    public Priority add(Priority priority) {
        return repository.save(priority);
    }

    @Transactional
    public Priority update(Priority priority) {
        return repository.save(priority);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Priority findById(Long id) {
        return repository.findById(id).get();
    }

    public List<Priority> find(String title, Long userId) {
        return repository.findByTitle(title, userId);
    }
}
