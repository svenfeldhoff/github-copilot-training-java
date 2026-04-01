# Module 8 - Prompt Files and Reusable Playbooks

## Goal
Build two reusable prompt files that any team member can invoke in Copilot Chat to get consistent, standards-aligned output — without repeating the same context every time.

## What Are Prompt Files?
Prompt files are Markdown files stored in `.github/prompts/` with the `.prompt.md` extension. Copilot picks them up automatically. In IntelliJ Copilot Chat you reference them by typing `#` and selecting the file from the picker — Copilot then uses the file's content as a structured instruction prefix for your request.

This is different from `.github/copilot-instructions.md` (which applies globally to every request) and from `.github/agents/` profiles (which configure a named agent). Prompt files are **on-demand**: you choose when to apply them.

---

## 8.1 Explore the Prepared Prompt Files
Two prompt files are already committed in this repository:

- `.github/prompts/api-contract-review.prompt.md` — reviews an endpoint against all project standards and returns findings ordered by severity with file + line references.
- `.github/prompts/integration-test-scaffold.prompt.md` — generates a complete `@SpringBootTest` + `MockMvc` test class with success, validation-failure, and error-path tests.

Open both files and read them fully before continuing. Notice:
- The **rules sections** mirror the constraints in `.github/copilot-instructions.md`.
- Each file specifies an **output format** so Copilot's response is predictable.

## 8.2 Run the Contract Review on an Existing Endpoint
Open Copilot Chat in **Ask** mode. Type the following exactly, then send:

```
#api-contract-review.prompt.md #TaskController.java
Review this controller.
```

Read the full output. Identify at least **two findings** — note their severity and file/line references. You will use them in 8.4.

> **Tip:** If the `#` picker does not show the file, type the filename after `#` to search for it.

## 8.3 Validate the Findings
For each finding from 8.2, open the referenced file and line and confirm whether the finding is accurate. Mark each one as:
- ✅ Valid — the issue exists as described.
- ⚠️ Partially valid — the issue exists but the suggested fix is wrong or incomplete.
- ❌ False positive — the code already satisfies the rule.

This is the manual review step that prompt files cannot replace.

## 8.4 Refine the Prompt — One Stronger Rule
Open `.github/prompts/api-contract-review.prompt.md`. Add one explicit rule that was missing or too vague in the output you received. Example ideas:
- Require that every `@Valid` parameter has an explicit 400-response integration test (not just any test).
- Require that the Swagger `@Operation` annotation is present on every endpoint method.
- Require that no `Optional` is returned directly from a controller (unwrap before responding).

Save the file, then re-run the exact same invocation from 8.2. Compare the two outputs:
- Did the new rule surface findings that were previously absent?
- Did anything regress (false positives introduced)?

## 8.5 Scaffold Tests for the Module 7 Endpoint
If you completed Module 7, you have a `/api/reports/summary` endpoint. Run:

```
#integration-test-scaffold.prompt.md #ReportController.java
Generate integration tests for the summary endpoint.
```

If you skipped Module 7, use `TaskController.java` instead.

Run `./mvnw test` immediately after applying the generated class. Note how many tests pass on the first try without any manual edits.

## 8.6 Fix and Finalize the Generated Tests
For any failing generated test, apply **one natural-language follow-up per failure**. Example:

> "The not-found test sends a GET to /api/reports/summary/999 but the endpoint does not accept an ID path variable — fix the test to use the correct URL."

Do not edit the test code directly — keep iterating with Copilot until all tests are green.

## 8.7 Make One Prompt Portable
Prompt files are most valuable when any team member gets the same quality output. Choose one of the two prompt files and add a short `## Context` section at the top that lists:
- The Java version (25).
- The framework (Spring Boot 4).
- The test library (JUnit 5 + MockMvc).

Re-run the prompt and confirm the output still meets the rules.

## 8.8 Debrief
Reflect on the experience:
- How did invoking `#api-contract-review.prompt.md` differ from just describing the same rules ad-hoc in Chat?
- Which rule in the prompt file produced the most useful output? Which produced a false positive?
- Would the integration-test scaffold be safe to apply without reading the generated code first? Why or why not?
- What is one rule you would add to either prompt file before sharing it with your whole team?
