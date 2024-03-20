package com.tunde.ToDoList.controllers;

import com.tunde.ToDoList.domain.dto.CreateTaskDto;
import com.tunde.ToDoList.domain.dto.UpdateTaskDto;
import com.tunde.ToDoList.domain.dto.UpdateTaskRequestDto;
import com.tunde.ToDoList.domain.entity.Task;
import com.tunde.ToDoList.domain.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskDto dto) throws Exception {
        var response = this.taskService.createTask(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable UUID id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.findById(id));
    }


    @PutMapping("/complete/{id}")
    public ResponseEntity<String> markTaskAsCompleted(@PathVariable UUID id) throws Exception {
        this.taskService.completeTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Done!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody UpdateTaskRequestDto dto) throws Exception {
        UpdateTaskDto updateTaskDto = new UpdateTaskDto(
                id,
                dto.title(),
                dto.description(),
                dto.dueDate()
        );

        return ResponseEntity.status(HttpStatus.OK).body(this.taskService.updateTask(updateTaskDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable UUID id) throws Exception {
        this.taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted!");
    }
}
