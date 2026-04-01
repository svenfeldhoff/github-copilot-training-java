# Module 2 - Context and Control

## Goal
Learn how to feed Copilot the right context and write precise prompts — so output matches project standards without manual correction.

## Why Context Matters
Copilot generates based on what it can see. The `.github/copilot-instructions.md` file sets standing rules. `#file` references add targeted scope. Without explicit context, Copilot guesses structure — you end up correcting instead of reviewing.

---

## 2.1 Read the Instruction File
Open `.github/copilot-instructions.md` and read every rule.

For each rule, answer: *"Would I recognise a violation of this rule in generated code?"* If not, note it down — that rule is most likely to slip through review.

## 2.2 Explain the Existing Controller
Select the entire `TaskController` class. Open Copilot Chat inline (`Alt+\`) and send:

```
/explain Describe all endpoints, request DTOs, response DTOs, and the controller → service flow. List any rule from .github/copilot-instructions.md that this class currently violates.
```

Note which violations (if any) are reported. You will check these again at the end.

## 2.3 Ask About Project Structure
In Chat panel **Ask** mode, send:

> "Which files in this project give you the most useful context when generating new code? List them with a one-line reason for each."

Compare the response to what you expected. Does Copilot mention `.github/copilot-instructions.md`?

## 2.4 Add an `owner` Field — First Pass
In Chat panel **Ask** mode, send a minimal prompt:

> "Add an `owner` field to the task API."

Accept nothing yet — just read the proposal. Note what is missing: validation? tests? service layer update?

## 2.5 Refine the Prompt and Execute
In Chat panel **Agent** mode, send the refined version:

> "Add a required `owner` field (non-blank string, max 100 characters) to the task API. Update the request DTO with Jakarta Validation, the service logic, and both unit and integration tests. Existing tests must still pass."

Let Agent mode apply the changes. Do not interrupt.

## 2.6 Run Tests and Validate
```bash
./mvnw test
```

For each failure, send one follow-up prompt describing only what is wrong — do not paste stack traces without context:

> "The integration test for POST /api/tasks fails because the request body in the test does not include the new `owner` field — fix the test."

Repeat until all tests pass.

## 2.7 Verify the Contract
Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and confirm:
- `owner` appears in the request schema for `POST /api/tasks`.
- `owner` appears in the response body.
- The endpoint still returns a JSON object, not plain text.

## Debrief
- How did the minimal prompt in 2.4 differ from the refined prompt in 2.5 in terms of output quality?
- Which part of the context (instructions file, selected code, `#file` reference) had the most impact?
- Did Copilot spot violations in 2.2 that you had not noticed yourself?
- What would you add to `.github/copilot-instructions.md` based on this exercise?
