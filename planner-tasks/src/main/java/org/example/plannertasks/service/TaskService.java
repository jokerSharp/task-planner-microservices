package org.example.plannertasks.service;

import lombok.AllArgsConstructor;

import org.example.plannertasks.repository.TaskRepository;
import org.example.taskplannerentity.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class TaskService {

    private final TaskRepository repository;

    public List<Task> findAll(Long userId) {
        return repository.findByUserIdOrderByTitleAsc(userId);
    }

    @Transactional
    public Task add(Task task) {
        return repository.save(task);
    }

    @Transactional
    public Task update(Task task) {
        return repository.save(task);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Page<Task> findByParams(String text, Boolean completed, Long priorityId, Long categoryId, Long userId,
                                   Date dateFrom, Date dateTo, PageRequest paging) {
        return repository.findByParams(text, completed, priorityId, categoryId, userId, dateFrom, dateTo, paging);
    }

    public Task findById(Long id) {
        return repository.findById(id).get();
    }
}
