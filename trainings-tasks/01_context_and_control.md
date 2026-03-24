# Module 1 - Context and Control

## Goal
Learn to guide Copilot with precise prompts and project context.

## Exercise

1. Review: Open and read the `.github/copilot-instructions.md` file.
2. Ask Copilot to explain the current API structure.
3. Chat: Ask: "Which file must I create next to satisfy Mandatory Coding Guidelines? Please provide the command to create it." Execute the suggested command.
4. Ask Copilot to add a new field `owner` to `TrainingTask`.
5. Refine the prompt to include validation and test updates.

## Prompt Starter

"Update the task API to support an `owner` field. Add validation, update service logic, and adjust unit/integration tests."

## Done Criteria

- `owner` appears in create/list responses.
- Validation errors are tested.
- No existing tests are broken.
