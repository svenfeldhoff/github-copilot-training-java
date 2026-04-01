# Module 6 - Agentic Workflow

## Goal
Delegate a full multi-step feature to Copilot Agent mode, supervise autonomous execution, recover from failures, and validate the result without manually writing a single line of code.

## What Makes Agentic Different
In Agent mode, Copilot reads files, edits code, and runs terminal commands in a loop until the task is done — or until it gets stuck. Your job shifts from writing to supervising: reviewing each step, checking what Agent touched, and catching decisions you disagree with before they compound.

---

## 6.1 Delegate Pagination in Agent Mode
Open Copilot Chat in **Agent** mode. Send:

> "Add pagination to GET /api/tasks using page and size query parameters with defaults of page=0 and size=10. Update the controller, service, and all affected tests. Run tests after each file change."

Do not touch any files while Agent is running. Watch which files it edits and in what order.

## 6.2 Simplify the Generated Logic
Select the pagination logic Agent added (usually in the service layer). Open inline chat and send:

```
/simplify Simplify this pagination logic while keeping page and size parameter handling and all existing test behaviour unchanged.
```

Compare the before and after. Accept the simplification only if it is genuinely clearer — not just shorter.

## 6.3 Fix a Security Finding with /fix
In Chat panel, send:

```
/fix POST /api/tasks currently returns the full task object including internal fields. Restrict the response to only id and status, and update integration tests to assert that title, description, and createdAt are absent from the response.
```

Review the diff before applying. Confirm tests are updated, not just deleted.

## 6.4 Failure-Recovery Drill
Intentionally break one test — comment out a valid assertion. Then ask Agent mode:

> "One test is failing. Triage the root cause, explain it in one sentence, then fix only the failing test without changing any production code."

Observe whether Agent identifies the real cause (the removed assertion) or over-corrects by changing production code. Revert if it over-corrects and ask again with a tighter constraint.

## 6.5 Run Full Verification
```bash
./mvnw clean verify
```

Confirm all tests pass and the JaCoCo report generates. Open `target/site/jacoco/index.html` and check whether coverage changed.

## 6.6 Review What Agent Touched
Look at `git diff` or the IntelliJ Git panel. For every file Agent edited, answer:
- Was this change expected?
- Does the change follow all rules in `.github/copilot-instructions.md`?
- Is there anything here you would not have written yourself and should escalate?

## 6.7 Verify the Updated API Contract
Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and confirm:
- Pagination parameters (`page`, `size`) appear on `GET /api/tasks`.
- `POST /api/tasks` response schema shows only `id` and `status`.

## Debrief
- Did Agent mode touch any files you did not expect? Were those changes safe?
- In the failure-recovery drill (6.4), did Agent find the real cause or over-correct?
- At what point in the execution would you have stopped Agent mode if this were production code?
- What is one constraint you would add to a future Agent prompt to reduce unsupervised risk?
