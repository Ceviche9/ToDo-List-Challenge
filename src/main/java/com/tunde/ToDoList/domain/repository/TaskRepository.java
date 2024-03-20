package com.tunde.ToDoList.domain.repository;

import com.tunde.ToDoList.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {
    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.completed = true, t.completedAt = CURRENT_DATE WHERE t.id = :taskId")
    void markTaskAsCompleted(UUID taskId);
}
