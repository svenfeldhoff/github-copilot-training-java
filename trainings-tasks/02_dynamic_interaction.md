# Module 2 - Dynamic Interaction Modes

## Goal
Practice different Copilot interaction styles inside IntelliJ.

## Exercise

1. Select a service method, open the inline chat (`Alt+Enter` → Copilot, or click the Copilot icon in the editor gutter) and prompt: "Add a JavaDoc comment to this method."
2. Use Chat panel to generate a new endpoint `GET /api/tasks/{id}`.
3. Use Agent mode in the Chat panel to generate the missing tests for the endpoint created in step 2.

## Done Criteria

- Each service method has a JavaDoc comment generated via inline chat.
- `GET /api/tasks/{id}` returns the full task as JSON or 404 if not found.
- Unit and integration tests for the new endpoint are generated and pass.
- `GET /api/tasks/{id}` is visible and testable at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

## Focus

- Inline chat is scoped to a selection — ideal for small, targeted changes.
- Chat panel is better for generating new code across multiple lines.
- Agent mode operates across multiple files autonomously — notice how it locates and updates the test classes without being told which files to edit.
