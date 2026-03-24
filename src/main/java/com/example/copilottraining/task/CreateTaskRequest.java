package com.example.copilottraining.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateTaskRequest(
    @NotBlank(message = "title is required")
    @Size(max = 120, message = "title must be at most 120 chars")
    String title,

    @Size(max = 500, message = "description must be at most 500 chars")
    String description
) {
}
