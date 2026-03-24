# Module 4 - Testing Framework

## Goal
Strengthen testing habits with Copilot support.

## Exercise

1. Ask Copilot to add a test for invalid task status update payload.
2. Write a failing integration test first for `POST /api/tasks` that expects a minimal response contract (`id`, `status`).
3. Ask Copilot to implement the minimal create-response DTO and update controller/service code to satisfy the new contract.
4. Add assertions that `title`, `description`, and `createdAt` are not returned by the create endpoint.
5. Add one edge-case unit test for `TaskService`.

## Prompt Starter

"Use test-first changes: update `TaskControllerIntegrationTest` so `POST /api/tasks` returns only `id` and `status` (no `title`, `description`, `createdAt`), then implement the minimal response DTO and required controller/service changes."

## Done Criteria

- At least one new failing test was written first.
- `POST /api/tasks` response exposes only `id` and `status`.
- Integration tests assert removed fields are absent.
- Test names clearly describe behavior.
- Tests are deterministic and independent.
