package com.tunde.ToDoList.domain.dto;

import java.sql.Date;
import java.util.UUID;

public record UpdateTaskDto(
        UUID id,
        String title,
        String description,
        Date dueDate
) {
}
