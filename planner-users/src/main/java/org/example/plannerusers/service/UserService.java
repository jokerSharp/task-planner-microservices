package org.example.plannerusers.service;

import lombok.RequiredArgsConstructor;
import org.example.plannerusers.repository.UserRepository;
import org.example.taskplannerentity.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository repository;

    public User findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Transactional
    public User add(User user) {
        return repository.save(user);
    }

    @Transactional
    public User update(User user) {
        return repository.save(user);
    }

    @Transactional
    public void deleteByUserId(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByUserEmail(String email) {
        repository.deleteByEmail(email);
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Page<User> findByParams(String username, String email, Pageable pageable) {
        return repository.findByParams(username, email, pageable);
    }
}
