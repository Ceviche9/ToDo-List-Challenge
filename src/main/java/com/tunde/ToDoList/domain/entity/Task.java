package com.tunde.ToDoList.domain.entity;

import com.tunde.ToDoList.domain.dto.CreateTaskDto;
import com.tunde.ToDoList.domain.dto.UpdateTaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private String description;

    private Priority priority = Priority.LOW;

    private Boolean completed = false;

    private Date dueDate;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Date completedAt;

    public Task(CreateTaskDto dto) {
        this.title = dto.title();
        this.description = dto.description();
        this.priority = dto.priority() == null ? Priority.LOW : dto.priority();
        this.dueDate = dto.dueDate();
    }
}