package com.tunde.ToDoList.domain.service;

import com.tunde.ToDoList.domain.dto.CreateTaskDto;
import com.tunde.ToDoList.domain.dto.UpdateTaskDto;
import com.tunde.ToDoList.domain.entity.Priority;
import com.tunde.ToDoList.domain.entity.Task;
import com.tunde.ToDoList.domain.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Should be able to create a task.")
    void createTask() throws Exception {
        CreateTaskDto request = this.generateRandomTask("Title-test");

        Task task = new Task(request);

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.createTask(request);

        assertNotNull(createdTask);
        assertEquals("Title-test", createdTask.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should be able to find all tasks.")
    void findAllTasks_Success() {
        List<Task> tasks = new ArrayList<>();
        CreateTaskDto createTaskDto1 = this.generateRandomTask("Title-test-1");
        CreateTaskDto createTaskDto2 = this.generateRandomTask("Title-test-2");
        tasks.add(new Task(createTaskDto1));
        tasks.add(new Task(createTaskDto2));
        when(taskRepository.findAll(Sort.by("dueDate"))).thenReturn(tasks);

        List<Task> result = taskService.findAllTasks();
        System.out.println(result.size());

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).findAll(Sort.by("dueDate"));
    }

    @Test
    @DisplayName("Should be able to find a task by id.")
    void findById_TaskExists_Success() throws Exception {
        UUID taskId = UUID.randomUUID();
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        Task task = new Task(createTaskDto);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        Task result = taskService.findById(taskId);

        assertNotNull(result);
        assertEquals("Title-test", result.getTitle());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    @DisplayName("Should not be able to find a task by id with a wrong id.")
    void findById_TaskDoesNotExist_ExceptionThrown() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> taskService.findById(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    @DisplayName("Should be able to mark a task as completed.")
    void completeTask_TaskExists_NotCompleted_Success() throws Exception {
        UUID taskId = UUID.randomUUID();
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        Task task = new Task(createTaskDto);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        taskService.completeTask(taskId);
        task.setCompleted(true);

        assertTrue(task.getCompleted());
    }

    @Test
    @DisplayName("Should not be able to mark a completed task as completed.")
    void completeTask_TaskAlreadyCompleted_ExceptionThrown() {
        // Given
        UUID taskId = UUID.randomUUID();
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        Task task = new Task(createTaskDto);
        task.setCompleted(true);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        assertThrows(Exception.class, () -> taskService.completeTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    @DisplayName("Should be able to delete a task.")
    void deleteTask_TaskExists_Success() throws Exception {
        UUID taskId = UUID.randomUUID();
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        Task task = new Task(createTaskDto);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Should not be able to delete a task that does not exists.")
    void deleteTask_TaskDoesNotExist_ExceptionThrown() {
        UUID taskId = UUID.randomUUID();
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> taskService.deleteTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    @DisplayName("Should be able to update a task.")
    void updateTask_TaskExists_Success() throws Exception {
        UpdateTaskDto dto = this.generateUpdateTaskDto(UUID.randomUUID());
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        Task task = new Task(createTaskDto);
        when(taskRepository.findById(dto.id())).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task updatedTask = taskService.updateTask(dto);

        assertNotNull(updatedTask);
        assertEquals(dto.title(), updatedTask.getTitle());
        verify(taskRepository, times(1)).findById(dto.id());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Should not be able to update a task that does not exists.")
    void updateTask_TaskDoesNotExist_ExceptionThrown() {
        UpdateTaskDto dto = this.generateUpdateTaskDto(UUID.randomUUID());
        CreateTaskDto createTaskDto = this.generateRandomTask("Title-test");
        when(taskRepository.findById(dto.id())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> taskService.updateTask(dto));
        verify(taskRepository, times(1)).findById(dto.id());
        verifyNoMoreInteractions(taskRepository);
    }

    private CreateTaskDto generateRandomTask(String title) {
        return new CreateTaskDto(
                title,
                "Title-description",
                Priority.MEDIUM,
                Date.valueOf("2025-05-23")
        );
    }

    private UpdateTaskDto generateUpdateTaskDto(UUID id) {
        return new UpdateTaskDto(
                id,
                "new-title",
                "new-description",
                Date.valueOf("2028-05-23")
        );
    }

}