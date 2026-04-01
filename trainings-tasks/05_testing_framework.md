# Module 5 - Test-Driven Development with Copilot

## Goal
Practice test-first development with Copilot — write the failing test before the implementation, use `/tests` for edge cases, and tighten the feedback loop between instruction changes and generated test quality.

## Why Test-First Matters With AI
When you write tests after implementation, Copilot generates tests that confirm what the code already does — including its bugs. Writing the failing test first forces you to define the contract before Copilot builds anything, which means generated code targets your intent, not its own output.

---

## 5.1 Write a Failing Integration Test First
Before touching any production code, open `TaskControllerIntegrationTest.java` and add a test that asserts `POST /api/tasks` returns **only** `id` and `status` — and that `title`, `description`, and `createdAt` are absent from the response body.

Run `./mvnw test` — confirm the test fails. Do not implement anything yet.

## 5.2 Implement to Make It Pass
In Chat panel **Agent** mode, send:

> "The integration test in TaskControllerIntegrationTest expects POST /api/tasks to return only id and status. Implement the minimal response DTO and update the controller and service to satisfy this contract. Do not change any other endpoint behaviour."

Run `./mvnw test` after the changes are applied. Fix failures with natural-language follow-ups until green.

## 5.3 Generate Edge-Case Tests with /tests
Select `TaskService#updateStatus` in the editor. Open inline chat and send:

```
/tests Generate tests for invalid status update scenarios: missing status field, unrecognised status value, and an attempt to transition from DONE back to OPEN. Each test must have a descriptive name and a comment explaining why this case matters.
```

Run the generated tests. Fix any that fail due to gaps in the service logic (not test errors).

## 5.4 Add a Negative-Path Integration Test
Add one more integration test manually that covers a validation failure you consider a real security or contract risk. Examples:
- `title` longer than the allowed maximum.
- Malformed JSON payload.
- Missing required field with a custom error message assertion.

This test must be written by you, not generated. Copilot can help with the assertion syntax, but the scenario choice is yours.

## 5.5 Generate an Uncovered Branch Test
Open `target/site/jacoco/index.html` and find the lowest-coverage method in `TaskService`. Select that method and send:

```
/tests Generate one unit test for the uncovered branch in this method. Explain in a comment which branch is being covered and why it was previously missed.
```

Run `./mvnw clean verify` and confirm the coverage percentage for that method increased.

## 5.6 Instruction-Tuning Loop
Open `.github/copilot-instructions.md` and add one explicit testing rule that would have improved the quality of tests generated in this module. Example:

> "Every test method name must follow the pattern `methodName_scenario_expectedOutcome`."

Re-run the `/tests` call from 5.3 on a different method. Compare the new output format to what was generated before the rule was added.

## 5.7 Final Coverage Check
```bash
./mvnw clean verify
```

Open `target/site/jacoco/index.html`. Confirm at least one coverage metric improved compared to the baseline from Module 0.

## Debrief
- Did writing the failing test first in 5.1 change how you thought about the implementation?
- How did the `/tests` output change after you updated the instruction file in 5.6?
- Which generated test from 5.3 was least useful? What was wrong with it?
- What is the risk of letting Copilot generate both the implementation and all its tests?
