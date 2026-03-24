package com.example.copilottraining.task;

import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTaskShouldReturn201AndCreatedTask() throws Exception {
        String body = objectMapper.writeValueAsString(Map.of(
            "title", "Run IntelliJ workshop",
            "description", "Demonstrate Copilot chat and completions"
        ));

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNotEmpty())
            .andExpect(jsonPath("$.title").value("Run IntelliJ workshop"))
            .andExpect(jsonPath("$.status").value("TODO"));
    }

    @Test
    void createTaskWithBlankTitleShouldReturn400() throws Exception {
        String body = objectMapper.writeValueAsString(Map.of(
            "title", "",
            "description", "No title is invalid"
        ));

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isBadRequest());
    }

    @Test
    void updateUnknownTaskShouldReturn404() throws Exception {
        String body = objectMapper.writeValueAsString(Map.of("status", "DONE"));

        mockMvc.perform(patch("/api/tasks/{id}/status", UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
            .andExpect(status().isNotFound());
    }

    @Test
    void listTasksShouldReturn200() throws Exception {
        mockMvc.perform(get("/api/tasks"))
            .andExpect(status().isOk());
    }
}
