package com.tunde.ToDoList.domain.service;

import com.tunde.ToDoList.domain.dto.CreateTaskDto;
import com.tunde.ToDoList.domain.dto.UpdateTaskDto;
import com.tunde.ToDoList.domain.entity.Task;
import com.tunde.ToDoList.domain.repository.TaskRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(CreateTaskDto dto) throws Exception {
        var task = new Task(dto);
        if (dto.title().isEmpty()) throw new BadRequestException("The task title can not be empty");
        return this.taskRepository.save(task);
    }

    public List<Task> findAllTasks() {
        Sort sortByDueDate = Sort.by("dueDate");
        return this.taskRepository.findAll(sortByDueDate);
    }

    public Task findById(UUID id) throws Exception {
        Optional<Task> task = this.taskRepository.findById(id);
        if (task.isEmpty()) throw new Exception("Task not found!");
        return task.get();
    }

    public void completeTask(UUID id) throws Exception {
        Optional<Task> optionalTask = this.taskRepository.findById(id);
        if (optionalTask.isEmpty()) throw new Exception("Task not found!");
        Task task = optionalTask.get();
        if (task.getCompleted()) throw new Exception("This task is already completed");
        task.setCompleted(true);
        this.taskRepository.save(task);
    }

    public void deleteTask(UUID id) throws Exception {
        Optional<Task> task = this.taskRepository.findById(id);
        if (task.isEmpty()) throw new Exception("Task not found!");
        this.taskRepository.delete(task.get());
    }

    public Task updateTask(UpdateTaskDto dto) throws Exception {
        Optional<Task> optionalTask = this.taskRepository.findById(dto.id());
        if (optionalTask.isEmpty()) throw new Exception("Task not found!");

        Task task = optionalTask.get();

        if (dto.title() != null && !dto.title().isEmpty()) {
            task.setTitle(dto.title());
        }

        if (dto.description() != null && !dto.description().isEmpty()) {
            task.setDescription(dto.description());
        }

        if (dto.dueDate() != null) {
            task.setDueDate(dto.dueDate());
        }

        return this.taskRepository.save(task);
    }
}
