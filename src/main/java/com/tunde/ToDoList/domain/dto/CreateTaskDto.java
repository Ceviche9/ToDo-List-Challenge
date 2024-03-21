package com.tunde.ToDoList.domain.dto;

import com.tunde.ToDoList.domain.entity.Priority;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;

public record CreateTaskDto(
        @NotBlank(message = "The task title can not be empty") String title,
        String description,
        @Enumerated(EnumType.STRING) Priority priority,
        Date dueDate
) {
}
