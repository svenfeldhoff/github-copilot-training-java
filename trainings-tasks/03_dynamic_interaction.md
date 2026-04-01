# Module 3 - Interaction Modes

## Goal
Practice every Copilot interaction surface in IntelliJ — inline chat, Chat panel Ask/Plan/Agent, and terminal — so you know exactly which tool to reach for in any situation.

## The Three Surfaces
- **Inline Chat** — scoped to a selection; best for understand-then-change on a single method or class.
- **Chat Panel** — full conversation with modes: Ask clarifies, Plan structures, Agent executes.
- **Terminal** — for verifying commands and reading test/build output as context.

Using the wrong surface adds noise and costs you review time.

---

## 3.1 Inline /explain on a Service Method
Open `TaskService.java`. Select one complete method. Open inline chat (`Alt+\`) and send:

```
/explain Describe what this method does, its side effects, and all edge cases that could cause incorrect behaviour.
```

Read the full response. Do not proceed to 3.2 until you could explain this method to a colleague without looking at the code.

## 3.2 Inline /doc on the Same Method
With the same method still selected, open inline chat again and send:

```
/doc Add Javadoc for this method including @param, @return, and @throws. Document the edge cases from the /explain output.
```

Accept the Javadoc. Verify it accurately reflects what the code actually does — correct any inaccuracies manually.

## 3.3 Chat Panel Ask — Design a New Endpoint
Switch to the Chat panel in **Ask** mode. Send:

> "Describe how to add `GET /api/tasks/{id}` to this project: which files need to change, what the response contract should be, and how to handle a missing task."

Read the design. Do not apply any code yet — this is a planning step.

## 3.4 Chat Panel Plan — Structure the Implementation
Switch to **Plan** mode. Send:

> "Create a 5-step implementation plan for GET /api/tasks/{id} covering: controller method, service method, 404 handling, unit test, and integration test."

Review the plan. If any step is missing or out of order, ask Copilot to revise it before executing.

## 3.5 Chat Panel Agent — Execute the Plan
Switch to **Agent** mode. Send:

> "Implement the planned GET /api/tasks/{id} endpoint across controller, service, and tests. Run tests after each file change and stop if tests fail."

Watch which files Agent touches and in what order. Do not interrupt — observe the full execution.

## 3.6 Verify with Terminal Commands
Ask Copilot Chat (Ask mode):

> "Give me the exact Maven command to run only the tests for TaskController and the exact command to run the full test suite."

Run both commands. Confirm `GET /api/tasks/{id}` tests pass and no existing tests regress.

## 3.7 Verify the New Endpoint
Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and confirm `GET /api/tasks/{id}` is listed. Test it with a valid ID and an unknown ID.

## Debrief
- When is inline chat a better choice than the Chat panel? Give a concrete rule.
- What did Plan mode reveal that you would have missed going straight to Agent mode?
- Did Agent mode touch any files you did not expect? Were those changes correct?
- Which mode produced output that needed the most manual review?
