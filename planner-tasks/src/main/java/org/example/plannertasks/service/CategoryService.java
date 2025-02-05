package org.example.plannertasks.service;

import lombok.AllArgsConstructor;
import org.example.plannertasks.repository.CategoryRepository;
import org.example.taskplannerentity.entity.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public List<Category> findAll(Long userId) {
        return repository.findByUserIdOrderByTitleAsc(userId);
    }

    @Transactional
    public Category add(Category category) {
        return repository.save(category);
    }

    @Transactional
    public Category update(Category category) {
        return repository.save(category);
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Category> findByTitle(String text, Long userId) {
        return repository.findByTitle(text, userId);
    }

    public Category findById(Long id) {
        return repository.findById(id).get();
    }
}
