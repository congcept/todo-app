package com.todo.controller;

import com.todo.dto.PagedResponse;
import com.todo.dto.TaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.model.TaskStatus;
import com.todo.service.TaskService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    path = "/api/tasks",
    produces = MediaType.APPLICATION_JSON_VALUE
)
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public PagedResponse<TaskResponse> getAllTasks(
        @RequestParam(required = false) TaskStatus status,
        @PageableDefault(
            sort = "createdAt",
            direction = Sort.Direction.DESC
        ) Pageable pageable
    ) {
        return taskService.getAllTasks(status, pageable);
    }

    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable UUID id) {
        return taskService.getTaskById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@Valid @RequestBody TaskRequest request) {
        return taskService.createTask(request);
    }

    @PutMapping("/{id}")
    public TaskResponse updateTask(
        @PathVariable UUID id,
        @Valid @RequestBody TaskRequest request
    ) {
        return taskService.updateTask(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
    }
}
