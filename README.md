# GitHub Copilot Training - Java Edition

This repository is a Java 25-first training project for teams that use **GitHub Copilot in IntelliJ IDEA**.

## Stack

- Java 25
- Spring Boot 4
- Maven Wrapper (`mvnw`, `mvnw.cmd`)
- JUnit 5 + Spring Boot Test

## What is included

- A small REST API for training tasks (`/api/tasks`)
- Unit and integration tests
- A structured training path in `trainings-tasks/`
- Copilot instructions for Java/Spring in `.github/copilot-instructions.md`
- Custom agent profiles in `.github/agents/` (for example, `.github/agents/myFixer.md` for focused fix workflows)
- MCP server config in `.github/copilot/mcp.json` (fetch + GitHub servers, used in Module 10)

## Quick Start

1. Clone the repository.
2. Open the project in IntelliJ IDEA (2025.2+ recommended).
3. Ensure JDK 25 is configured.
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

1. `trainings-tasks/00_prerequisites_intro.md` — Prerequisites and Setup
2. `trainings-tasks/01_copilot_actions_reference.md` — Copilot Actions Reference *(keep open as a cheat sheet)*
3. `trainings-tasks/02_context_and_control.md` — Context and Control
4. `trainings-tasks/03_dynamic_interaction.md` — Interaction Modes
5. `trainings-tasks/04_version_control.md` — Version Control and Review
6. `trainings-tasks/05_testing_framework.md` — Test-Driven Development with Copilot
7. `trainings-tasks/06_agentic_workflow.md` — Agentic Workflow
8. `trainings-tasks/07_vibe_coding.md` — Vibe Coding
9. `trainings-tasks/08_prompt_files_and_playbooks.md` — Prompt Files and Reusable Playbooks
10. `trainings-tasks/09_subagents_and_capstone.md` — Custom Agents and Capstone
11. `trainings-tasks/10_mcp_tool_integration.md` — MCP Tool Integration

## Notes

Generated output from Copilot must always be reviewed, tested, and adapted to your team's coding standards.
