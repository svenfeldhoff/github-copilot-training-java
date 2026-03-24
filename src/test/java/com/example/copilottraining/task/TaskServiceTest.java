package com.example.copilottraining.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskServiceTest {

    private final TaskService taskService = new TaskService();

    @Test
    void createTaskShouldReturnTaskWithDefaultStatusTodo() {
        TrainingTask createdTask = taskService.createTask(new CreateTaskRequest("Prepare workshop", "Create Spring Boot exercises"));

        assertNotNull(createdTask.id());
        assertEquals("Prepare workshop", createdTask.title());
        assertEquals(TaskStatus.TODO, createdTask.status());
        assertNotNull(createdTask.createdAt());
    }

    @Test
    void listTasksShouldContainPreviouslyCreatedTask() {
        taskService.createTask(new CreateTaskRequest("Task A", ""));

        assertFalse(taskService.listTasks().isEmpty());
    }
}
