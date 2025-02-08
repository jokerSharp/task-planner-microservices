package org.example.plannertasks.controller;

import lombok.AllArgsConstructor;
import org.example.plannertasks.requests.TaskSearchValues;
import org.example.plannertasks.service.TaskService;
import org.example.plannerutils.client.UserWebClientBuilder;
import org.example.taskplannerentity.entity.Task;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@AllArgsConstructor
@RestController
@RequestMapping("api/tasks")
public class TaskController {

    public static final String ID_COLUMN = "id";

    private final TaskService service;
    private final UserWebClientBuilder userWebClientBuilder;

    @PostMapping("/all")
    public ResponseEntity<List<Task>> findAll(@RequestBody Long userId) {
        return ResponseEntity.ok(service.findAll(userId));
    }

    @PostMapping("/add")
    public ResponseEntity<Task> add(@RequestBody Task task) {
        if (task.getId() != null && task.getId() != 0) {
            return new ResponseEntity("redundant param: id MUST be null", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().length() == 0) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        if (userWebClientBuilder.userExists(task.getUserId())) {
            return ResponseEntity.ok(service.add(task));
        }
        return new ResponseEntity("user with id=%d is not found".formatted(task.getUserId()), HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<Task> update(@RequestBody Task task) {
        if (task.getId() == null || task.getId() == 0) {
            return new ResponseEntity("missed param: id", HttpStatus.NOT_ACCEPTABLE);
        }
        if (task.getTitle() == null || task.getTitle().trim().isEmpty()) {
            return new ResponseEntity("missed param: title", HttpStatus.NOT_ACCEPTABLE);
        }
        service.update(task);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        try {
            service.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/id")
    public ResponseEntity<Task> findById(@RequestBody Long id) {
        Task task = null;
        try {
            task = service.findById(id);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return new ResponseEntity("id=" + id + " not found", HttpStatus.NOT_ACCEPTABLE);
        }
        return ResponseEntity.ok(task);
    }

    @PostMapping("/search")
    public ResponseEntity<Page<Task>> search(@RequestBody TaskSearchValues taskSearchValues) {
        String title = taskSearchValues.getTitle() != null ? taskSearchValues.getTitle() : null;
        Boolean completed = taskSearchValues.getCompleted() != null ? taskSearchValues.getCompleted() : null;
        Long priorityId = taskSearchValues.getPriorityId() != null ? taskSearchValues.getPriorityId() : null;
        Long categoryId = taskSearchValues.getCategoryId() != null ? taskSearchValues.getCategoryId() : null;

        String sortColumn = taskSearchValues.getSortColumn() != null ? taskSearchValues.getSortColumn() : null;
        String sortDirection = taskSearchValues.getSortDirection() != null ? taskSearchValues.getSortDirection() : null;

        Integer pageNumber = taskSearchValues.getPageNumber() != null ? taskSearchValues.getPageNumber() : null;
        Integer pageSize = taskSearchValues.getPageSize() != null ? taskSearchValues.getPageSize() : null;

        Long userId = taskSearchValues.getUserId() != null ? taskSearchValues.getUserId() : null;
        if (userId == null) {
            return new ResponseEntity("missed param: userId", HttpStatus.NOT_ACCEPTABLE);
        }

        Date dateFrom = null;
        Date dateTo = null;

        if (taskSearchValues.getDateFrom() != null) {
            Calendar calendarFrom = Calendar.getInstance();
            calendarFrom.setTime(taskSearchValues.getDateFrom());
            calendarFrom.set(Calendar.HOUR_OF_DAY, 0);
            calendarFrom.set(Calendar.MINUTE, 0);
            calendarFrom.set(Calendar.SECOND, 0);
            calendarFrom.set(Calendar.MILLISECOND, 0);
            dateFrom = calendarFrom.getTime();
        }

        if (taskSearchValues.getDateTo() != null) {
            Calendar calendarTo = Calendar.getInstance();
            calendarTo.setTime(taskSearchValues.getDateTo());
            calendarTo.set(Calendar.HOUR_OF_DAY, 23);
            calendarTo.set(Calendar.MINUTE, 59);
            calendarTo.set(Calendar.SECOND, 59);
            calendarTo.set(Calendar.MILLISECOND, 999);
            dateTo = calendarTo.getTime();
        }

        Sort.Direction direction = sortDirection == null
                || sortDirection.trim().isEmpty()
                || sortDirection.trim().equals("asc")
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortColumn, ID_COLUMN);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Task> result = service.findByParams(title, completed, priorityId, categoryId, userId, dateFrom, dateTo, pageRequest);

        return ResponseEntity.ok(result);
    }
}
