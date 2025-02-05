package org.example.plannertasks.controller;

import lombok.AllArgsConstructor;
import org.example.plannertasks.service.StatService;
import org.example.taskplannerentity.entity.Stat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class StatController {

    private final StatService statService;

    @PostMapping("api/stats")
    public ResponseEntity<Stat> findByEmail(@RequestBody Long userId) {
        return ResponseEntity.ok(statService.findStat(userId));
    }
}
