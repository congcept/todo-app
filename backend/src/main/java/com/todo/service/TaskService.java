package com.todo.service;

import com.todo.dto.PagedResponse;
import com.todo.dto.TaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.model.TaskStatus;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    PagedResponse<TaskResponse> getAllTasks(
        TaskStatus status,
        Pageable pageable
    );
    TaskResponse getTaskById(UUID id);
    TaskResponse createTask(TaskRequest request);
    TaskResponse updateTask(UUID id, TaskRequest request);
    void deleteTask(UUID id);
}
