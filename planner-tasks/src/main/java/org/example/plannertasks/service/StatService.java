package org.example.plannertasks.service;

import lombok.AllArgsConstructor;
import org.example.plannertasks.repository.StatRepository;
import org.example.taskplannerentity.entity.Stat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class StatService {

    private final StatRepository repository;

    public Stat findStat(Long userId) {
        return repository.findByUserId(userId);
    }
}
