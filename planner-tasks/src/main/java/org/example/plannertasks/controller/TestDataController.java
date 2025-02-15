package org.example.plannertasks.controller;

import lombok.RequiredArgsConstructor;
import org.example.plannertasks.service.TestDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/test-data")
public class TestDataController {

    private final TestDataService testDataService;

    @PostMapping("/init")
    public ResponseEntity<Boolean> init(@RequestBody Long userId) {

        testDataService.initTestData(userId);
        return ResponseEntity.ok(true);
    }
}
