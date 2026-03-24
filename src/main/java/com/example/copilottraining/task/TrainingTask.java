package com.example.copilottraining.task;

import java.time.Instant;
import java.util.UUID;

public record TrainingTask(
    UUID id,
    String title,
    String description,
    TaskStatus status,
    Instant createdAt
) {
}
