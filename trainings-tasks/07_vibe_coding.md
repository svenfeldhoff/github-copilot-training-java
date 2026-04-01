# Module 7 - Vibe Coding

## Goal
Experience full AI-driven feature delivery: describe loosely, let Copilot build everything, iterate fast, then harden only what matters.

## What is Vibe Coding?
Vibe coding means you supply intent in natural language and let the model drive the entire implementation — files, wiring, tests included. You steer by reacting to output, not by planning in detail upfront. The discipline comes *after* the first pass, not before.

---

## 7.1 Set the Vibe
Open Copilot Chat in **Agent** mode. Write a single, loose natural-language prompt — no file names, no method names, no structure hints. Example:

> "Add a dashboard summary endpoint that returns total tasks, how many are done, and the completion rate as a percentage. Keep it simple."

Submit it and do **not** edit the prompt. Let Copilot choose where and how to build it.

## 7.2 Watch the Build
Observe what Copilot creates without interrupting:
- Which files does it create or edit?
- Does it wire the controller, service, and any DTO without being told?
- Does it add tests unprompted?

Note down anything surprising or missing — you will use these observations in 7.4.

## 7.3 Run and See
Start the app and hit the endpoint:

```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080/api/reports/summary](http://localhost:8080/api/reports/summary) and check the raw JSON. Also run:

```bash
./mvnw test
```

Accept the current state — do not fix anything manually yet.

## 7.4 Iterate With Natural Language
Based on your 7.2 observations, send one follow-up prompt per issue, in plain language. Examples:

> "The completion rate should be 0 when there are no tasks, not a division error."

> "The response is missing a field for in-progress tasks — add it."

> "The service logic is inline in the controller — move it to a separate service class."

Keep prompts short and reactive. Repeat until the endpoint behaves correctly.

## 7.5 Ask for Tests
If tests were not generated in 7.1, ask now:

> "Generate unit tests for the summary service covering: empty task list, all tasks done, mixed statuses. Also add one integration test for GET /api/reports/summary."

Run tests and iterate with natural-language follow-ups until they are green.

## 7.6 Harden One Thing
Pick **one** area where the vibe-coded output cut corners and fix it properly with Copilot's help. Examples:
- Add a missing validation or edge-case test.
- Improve the response model (rename a field, add a description for Swagger).
- Enforce the controller → service boundary if it was skipped.

Only fix one thing — resist the urge to refactor everything.

## 7.7 Verify the Contract
Open Swagger UI and confirm the endpoint is visible and returns the correct schema:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## 7.8 Debrief
Reflect on the experience:
- What did vibe coding accelerate compared to writing everything yourself?
- Where did you have to apply manual judgment that Copilot could not supply?
- What would have gone wrong if you had shipped the 7.1 output directly?
- How did your follow-up prompts in 7.4 compare to the quality of your original prompt?
