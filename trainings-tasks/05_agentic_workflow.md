# Module 5 - Agentic Workflow

## Goal
Delegate multi-step tasks to Copilot and supervise the output.

## Exercise

1. Ask Copilot to add pagination to `GET /api/tasks`.
2. Require updates across controller, service, and tests.
3. Review generated implementation and request targeted refactor.
4. Use Copilot Chat `/fix`: "Resolve security finding: `POST /api/tasks` overexposes response fields; return only `id` and `status`, and update integration tests to assert `title`, `description`, and `createdAt` are absent."

## Review Checklist

- API contract remains backward compatible or clearly versioned.
- Validation and error handling still work.
- Performance is acceptable for in-memory storage.
- The `/fix` change is minimal, passes tests, and does not alter unrelated endpoints.
- Pagination parameters and updated `POST` response schema are visible at [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).
