# Module 9 - Custom Agents and Capstone

## Goal
Build and use custom agent profiles, run a structured multi-agent workflow, and deliver a fully validated feature increment as a capstone.

## What Custom Agents Add
A named agent profile (`.github/agents/*.md`) lets you define a reusable specialist: fixed role, fixed constraints, fixed output format. Instead of retyping context each session, you pick the agent and it brings its own rules. This module puts that into practice on a real feature.

---

## 9.1 Review the Prepared Agent Profile
Open `.github/agents/myFixer.md`. Read the role, constraints, and output expectations.

Answer: which rules in `myFixer.md` are stricter than the global `.github/copilot-instructions.md`? Which are the same?

## 9.2 Choose a Feature Increment
Pick one of the following (or propose your own):
- Task search filters: `GET /api/tasks?status=OPEN&owner=alice`
- Owner-based query endpoint: `GET /api/tasks/owner/{owner}`
- Status transition audit: log every status change with timestamp and previous value

The feature must require changes in controller, service, and tests.

## 9.3 Use Plan Mode to Scope the Work
In Chat panel **Plan** mode, send:

> "Create a 6-step implementation plan for [your chosen feature]. Include: affected files, validation requirements, test scenarios (success + failure + edge), and one risk per step."

Review the plan critically. Ask Copilot to revise any step that is too vague or skips a layer.

## 9.4 Delegate Research
In Chat panel **Agent** mode with `@project` context, send:

> "Research the existing patterns for this feature: which files would be affected, what validation annotations are already in use, and what test structure do the existing integration tests follow? Return findings only — no code changes."

Use the research output to refine the plan from 9.3 if needed.

## 9.5 Delegate Implementation
Switch the Chat panel to the **myFixer** agent profile (agent picker in the Chat header). Send:

> "Implement [your chosen feature] following the plan. Apply changes to controller, service, DTOs, and tests. Run tests after each file group and stop if tests fail."

Watch which files are touched. Do not interrupt unless Agent produces an obviously wrong file change.

## 9.6 Delegate Review
In Chat panel **Ask** mode with `#api-contract-review.prompt.md` attached, send:

> "#api-contract-review.prompt.md #[YourController].java Review the implementation delivered in the previous session."

Read every finding. Apply fixes for Critical and Warning findings before continuing.

## 9.7 Consolidate and Validate
Apply only the findings you confirmed as real (from 9.6). Run:

```bash
./mvnw clean verify
```

All tests must pass. Open `target/site/jacoco/index.html` and confirm no coverage regression.

## 9.8 Verify the Delivered Contract
Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) and confirm the new endpoint is listed, has the correct schema, and responds correctly to at least one live request.

## Debrief
- Did the `myFixer` agent profile change the quality or format of generated code compared to default Agent mode?
- Which phase (research / implementation / review) contributed the most value?
- What finding from 9.6 would have reached production if you had skipped the review phase?
- What custom agent profile would you create next for your real project?

---

## Capstone Scoring Rubric (1–5)

| Dimension | What earns a 5 |
|-----------|---------------|
| **Correctness** | Feature works end-to-end with no regressions |
| **Test Quality** | Success + validation failure + error path all covered |
| **Prompt Quality** | Prompts are specific, contextual, and produced minimal rework |
| **Review Discipline** | Every finding classified; no finding accepted blindly |
| **Risk Awareness** | At least one risk identified and explicitly mitigated |
