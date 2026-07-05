package com.todo.service;

import com.todo.dto.PagedResponse;
import com.todo.dto.TaskRequest;
import com.todo.dto.TaskResponse;
import com.todo.exception.TaskNotFoundException;
import com.todo.model.Task;
import com.todo.model.TaskStatus;
import com.todo.repository.TaskRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public PagedResponse<TaskResponse> getAllTasks(
        TaskStatus status,
        Pageable pageable
    ) {
        Page<Task> taskPage =
            status == null
                ? taskRepository.findAll(pageable)
                : taskRepository.findAllByStatus(status, pageable);

        return PagedResponse.from(
            taskPage,
            taskPage.map(TaskResponse::from).getContent()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponse getTaskById(UUID id) {
        return taskRepository
            .findById(id)
            .map(TaskResponse::from)
            .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskRequest request) {
        Task task = Task.builder()
            .title(request.title())
            .description(request.description())
            .status(
                request.status() != null ? request.status() : TaskStatus.PENDING
            )
            .build();
        return TaskResponse.from(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskResponse updateTask(UUID id, TaskRequest request) {
        Task task = taskRepository
            .findById(id)
            .orElseThrow(() -> new TaskNotFoundException(id));

        task.setTitle(request.title());
        task.setDescription(request.description());
        if (request.status() != null) {
            task.setStatus(request.status());
        }

        return TaskResponse.from(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(id);
        }
        taskRepository.deleteById(id);
    }
}
