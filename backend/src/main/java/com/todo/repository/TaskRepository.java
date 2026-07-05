package com.todo.repository;

import com.todo.model.Task;
import com.todo.model.TaskStatus;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    Page<Task> findAllByStatus(TaskStatus status, Pageable pageable);
}
