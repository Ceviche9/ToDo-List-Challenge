package com.tunde.ToDoList.domain.dto;

import com.tunde.ToDoList.domain.entity.Priority;

import java.sql.Date;

public record CreateTaskDto(
        String title,
        String description,
        Priority priority,
        Date dueDate
) {
}
