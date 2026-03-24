# GitHub Copilot Instructions for Java Training Project

## Project Context

This project is used to train developers to work effectively with GitHub Copilot in IntelliJ IDEA.

## Mandatory Guidelines

1. Use Java 25 language features when they improve readability.
2. Keep Spring components focused and testable (controller -> service separation).
3. All public methods should have clear names and explicit return types.
4. Add or update tests for behavior changes.
5. Prefer constructor injection over field injection.
6. API endpoints should return JSON objects/arrays, not plain text for business responses.
7. Validate request DTOs using Jakarta Validation annotations.

## Testing Rules

- Unit tests for service logic.
- Integration tests for API behavior (`@SpringBootTest` + `MockMvc` or `WebTestClient`).
- Cover success path, validation failures, and error paths.

## Copilot Usage

- Ask Copilot for small, test-first increments.
- Request refactor suggestions after tests are green.
- Always manually review generated code for security and maintainability.
