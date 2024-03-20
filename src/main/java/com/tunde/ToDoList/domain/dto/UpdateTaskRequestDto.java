package com.tunde.ToDoList.domain.dto;

import java.sql.Date;

public record UpdateTaskRequestDto(
        String title,
        String description,
        Date dueDate
) {
}
