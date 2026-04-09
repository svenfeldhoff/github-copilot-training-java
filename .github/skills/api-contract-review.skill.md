# Skill: API Contract Review

## Skill objective
> "This skill reviews a Spring REST controller and its tests and always returns a structured findings report."

This skill is intentionally stricter than the prompt-file variant of API contract review. Use it when you want a repeatable workflow with defined inputs, ordered analysis steps, and deterministic output.

---

## When to use
Use this skill when:
- Reviewing a new or changed Spring `@RestController` and its associated tests
- Verifying that integration tests cover every endpoint, every validation rule, and every error path
- Preparing a PR review checklist for API-layer changes

## Do not rules (strict)
Do **not** do any of the following:
- Suggest internal refactors unrelated to the API contract (naming, field ordering, etc.)
- Review service-layer or repository-layer logic
- Evaluate performance or security beyond HTTP status correctness
- Infer behavior that is not visible in the provided files
- Claim tests exist unless a matching test method is present in the integration test file
- Propose implementation changes in the findings section (keep findings diagnostic only)

If an item cannot be verified from provided files, write: `Not verifiable from provided inputs.`

---

## Required inputs
| # | Input | Description |
|---|-------|-------------|
| 1 | Controller file path | The `@RestController` class to analyse |
| 2 | Service file path | The `@Service` class called by the controller |
| 3 | Integration test file path | The `@SpringBootTest` / MockMvc test class for the controller |

Optional: DTO files (`CreateTaskRequest`, `UpdateTaskStatusRequest`, etc.) if not resolvable from context.

---

## Severity definitions
| Level | Assign this level when... |
|-------|---------------------------|
| **Critical** | Contract correctness is broken or likely broken at runtime: wrong HTTP status, missing validation trigger, unhandled mapped exception (500 instead of mapped status), or response shape contradicts documented/tested contract. |
| **Warning** | Contract may be correct but evidence is insufficient: missing integration test for an endpoint path type, incomplete assertions on error body contract, or DTO validation rule with no corresponding failure-path test. |
| **Note** | Non-breaking contract hygiene issue only: inconsistent response style, minor naming mismatch, or low-risk documentation/test clarity issue. |

Severity tie-breaker rule:
- If a finding could be **Critical** or **Warning**, choose **Critical**.
- If a finding could be **Warning** or **Note**, choose **Warning**.

---

## Analysis steps
Execute every step in order. Report findings grouped by step in the output.

### Step 1 – Endpoint inventory
List every HTTP method + path combination exposed by the controller.  
For each endpoint record:
- HTTP method and path
- Expected success status code (e.g. `201 Created`, `200 OK`)
- Request body DTO (if any) and whether `@Valid` is present
- Return type

### Step 2 – DTO validation rules
For each DTO used as a request body:
- List every Jakarta Validation annotation and the field it guards
- Note any field that has **no** validation annotation

### Step 3 – Exception handler coverage
Inspect the `@RestControllerAdvice` / `@ExceptionHandler` methods.  
For each handler record:
- Exception type caught
- HTTP status returned
- Response body type (e.g. `ProblemDetail`)

### Step 4 – Test coverage matrix
Build a matrix: **endpoint × path type** (success / validation-failure / not-found / other-error).  
Mark each cell: ✅ covered | ⚠️ partial | ❌ missing.

### Step 5 – Mismatch detection
Compare the inventory from Steps 1–3 against the matrix from Step 4.  
For each mismatch, assign severity using the table above and include file path and line reference when available.

---

## Output format
Return findings in **exactly** this order and structure. Do not add extra sections.

Deterministic output rules:
- Output only sections `1` to `7` below. No intro, no outro.
- Use `None.` for any empty list/table/matrix bucket.
- In sections with endpoint rows, sort by path alphabetically, then method (`GET`, `POST`, `PATCH`, `PUT`, `DELETE`).
- In `Findings`, sort by severity (`Critical`, `Warning`, `Note`), then by endpoint path, then by finding ID.
- Finding IDs must be sequential per severity: `C1..Cn`, `W1..Wn`, `N1..Nn`.

### 1. Endpoint inventory
_(table produced in Step 1)_

### 2. DTO validation summary
_(table produced in Step 2)_

### 3. Exception handler summary
_(table produced in Step 3)_

### 4. Test coverage matrix
_(matrix produced in Step 4)_

### 5. Findings

#### Critical
- **[C1]** _description_ — _file:line_ — _endpoint_

#### Warning
- **[W1]** _description_ — _file:line_ — _endpoint_

#### Note
- **[N1]** _description_ — _file:line_ — _endpoint_

_If a severity bucket is empty, write "None."_

### 6. Missing tests
List each missing test as a one-line test-method name suggestion:
```
void <methodName>ShouldReturn<StatusCode>When<Condition>()
```

### 7. Suggested next action
One concrete, prioritized action to take immediately (implement a specific test, add `@Valid`, add an `@ExceptionHandler`, etc.).

---

## Invocation example

```
#api-contract-review.skill.md `src/main/java/com/example/copilottraining/task/TaskController.java` `src/main/java/com/example/copilottraining/task/TaskService.java` `src/test/java/com/example/copilottraining/task/TaskControllerIntegrationTest.java`
```

### Quick two-file variant (omit service)
```
#api-contract-review.skill.md `src/main/java/com/example/copilottraining/task/TaskController.java` `src/test/java/com/example/copilottraining/task/TaskControllerIntegrationTest.java`
```

---

## Project-specific notes (copilot-training-java)
- DTOs are Java records annotated with Jakarta Validation (`@NotBlank`, `@Size`, `@NotNull`).
- The global exception handler (`ApiExceptionHandler`) returns `ProblemDetail` for `NoSuchElementException` (404) and `MethodArgumentNotValidException` (400).
- All endpoints live under `/api/tasks`.
- Integration tests use `@SpringBootTest` + `@AutoConfigureMockMvc` + `MockMvc`.
- New tests must follow the naming convention: `<action><Condition>ShouldReturn<StatusCode>`.

