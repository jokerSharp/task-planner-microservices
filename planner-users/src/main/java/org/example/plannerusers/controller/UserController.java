package org.example.plannerusers.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.plannerusers.request.UserSearchValues;
import org.example.plannerusers.service.UserService;
import org.example.plannerutils.client.UserWebClientBuilder;
import org.example.taskplannerentity.entity.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {

    public static final String ID_COLUMN = "id";

    private final UserService service;
    private final UserWebClientBuilder userWebClientBuilder;

    @PostMapping("/add")
    public ResponseEntity<User> add(@RequestBody User user) {
        if (user.getId() != null && user.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity("missing param: email MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity("missing param: password MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return new ResponseEntity("missing param: username MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        user = service.add(user);
        if (user != null) {
            userWebClientBuilder.initUserData(user.getId())
                    .subscribe(result -> log.info("user is populated: {}", result));
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        if (user.getId() == null && user.getId() == 0) {
            return new ResponseEntity("missing param: id MUST not be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return new ResponseEntity("missing param: email MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return new ResponseEntity("missing param: password MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (user.getUsername() == null || user.getUsername().trim().isEmpty()) {
            return new ResponseEntity("missing param: username MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(service.update(user));
    }

    @PostMapping("/delete-by-id")
    public ResponseEntity deleteById(@RequestBody Long userId) {
        try {
            service.deleteByUserId(userId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/delete-by-email")
    public ResponseEntity deleteByEmail(@RequestBody String email) {
        try {
            service.deleteByUserEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity findById(@RequestBody Long userId) {
        User user = null;
        try {
            user = service.findById(userId);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/email")
    public ResponseEntity findByEmail(@RequestBody String email) {
        User user = null;
        try {
            user = service.findByEmail(email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<User>> search(@RequestBody UserSearchValues userSearchValues) {
        String email = userSearchValues.getEmail() != null ? userSearchValues.getEmail() : null;
        String username = userSearchValues.getUsername() != null ? userSearchValues.getUsername() : null;

        if (email == null || email.trim().isEmpty()) {
            return new ResponseEntity("missing param: email MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (username == null || username.trim().isEmpty()) {
            return new ResponseEntity("missing param: username MUST be not null", HttpStatus.NOT_ACCEPTABLE);
        }

        String sortColumn = userSearchValues.getSortColumn() != null ? userSearchValues.getSortColumn() : null;
        String sortDirection = userSearchValues.getSortDirection() != null ? userSearchValues.getSortDirection() : null;

        Integer pageNumber = userSearchValues.getPageNumber() != null ? userSearchValues.getPageNumber() : null;
        Integer pageSize = userSearchValues.getPageSize() != null ? userSearchValues.getPageSize() : null;

        Sort.Direction direction = sortDirection == null
                || sortDirection.trim().isEmpty()
                || sortDirection.trim().equals("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> result = service.findByParams(email, username, pageRequest);

        return ResponseEntity.ok(result);
    }
}
