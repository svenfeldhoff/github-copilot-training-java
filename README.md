# GitHub Copilot Training - Java Edition

This repository is a Java-first training project for teams that use **GitHub Copilot in IntelliJ IDEA**.

## Stack

- Java 21
- Spring Boot 4
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- JUnit 5 + Spring Boot Test

## What is included

- A small REST API for training tasks (`/api/tasks`)
- Unit and integration tests
- A structured training path in `trainings-tasks/`
- Copilot instructions for Java/Spring in `.github/copilot-instructions.md`

## Quick Start

1. Clone the repository.
2. Open the project in IntelliJ IDEA (2025.2+ recommended).
3. Ensure JDK 21 is configured.
4. Run tests:

```bash
./mvnw test
```

5. Start the app:

```bash
./mvnw spring-boot:run
```

6. Open:
- http://localhost:8080/api/tasks
- http://localhost:8080/actuator/health

## Suggested Training Flow

1. `trainings-tasks/00_prerequisites_intro.md`
2. `trainings-tasks/01_context_and_control.md`
3. `trainings-tasks/02_dynamic_interaction.md`
4. `trainings-tasks/03_version_control.md`
5. `trainings-tasks/04_testing_framework.md`
6. `trainings-tasks/05_agentic_workflow.md`
7. `trainings-tasks/06_vibe_coding.md`

## Notes

Generated output from Copilot must always be reviewed, tested, and adapted to your team's coding standards.
