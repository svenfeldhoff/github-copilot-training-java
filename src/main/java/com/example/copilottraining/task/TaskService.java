package com.example.copilottraining.task;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class TaskService {

    private final ConcurrentMap<UUID, TrainingTask> tasks = new ConcurrentHashMap<>();

    public TrainingTask createTask(CreateTaskRequest request) {
        UUID id = UUID.randomUUID();
        TrainingTask task = new TrainingTask(id, request.title(), request.description(), TaskStatus.TODO, Instant.now());
        tasks.put(id, task);
        return task;
    }

    public List<TrainingTask> listTasks() {
        return tasks.values().stream()
            .sorted(Comparator.comparing(TrainingTask::createdAt).reversed())
            .toList();
    }

    public TrainingTask updateStatus(UUID id, UpdateTaskStatusRequest request) {
        TrainingTask existing = tasks.get(id);
        if (existing == null) {
            throw new NoSuchElementException("Task with id " + id + " not found");
        }

        TrainingTask updated = new TrainingTask(
            existing.id(),
            existing.title(),
            existing.description(),
            request.status(),
            existing.createdAt()
        );
        tasks.put(id, updated);
        return updated;
    }
}
