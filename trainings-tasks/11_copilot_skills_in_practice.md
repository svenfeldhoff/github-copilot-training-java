# Module 11 - Copilot Skills: Write and Use

## Goal
Learn how to create a reusable Copilot skill and apply it consistently in Ask and Agent workflows.

## What you will be able to do in this module
By the end of this module, you will be able to:
- Define a skill with clear scope, inputs, and output format
- Write a reusable skill file that other developers can invoke
- Use the skill in chat to review code and drive implementation tasks
- Iterate on the skill based on real results

---

## What we mean by "skill" in this training
In this project, a skill is a reusable prompt artifact with a strict structure and expected behavior.

For hands-on practice, you will implement a skill as a markdown prompt file in the repository and invoke it with `#filename` in Copilot Chat.

This gives you a practical workflow you can version, review, and improve with your team.

---

## Prerequisites
Before 11.1, confirm:
- You completed Module 8 (prompt files), Module 9 (agents), and Module 10 (MCP tools)
- The project opens in IntelliJ and Copilot Chat is available
- You can run tests locally with Maven wrapper

---

## 11.1 Choose One Repetitive Task
Pick one task that your team repeats often. Examples:
- API contract review for a controller
- Test gap analysis after a feature change
- Pull request risk triage

Write a one-sentence skill objective:
> "This skill helps me [do X] and always returns [Y format]."

## 11.2 Write the First Skill File
Create `.github/skills/api-contract-review.skill.md` with this starter template:

```markdown
# Skill: API Contract Review

## When to use
Use this skill when reviewing a Spring REST controller and its tests.

## Required inputs
- Controller file path
- Related service file path
- Integration test file path

## Steps
1. Identify endpoints and expected status codes.
2. Compare validation rules in DTOs against controller behavior.
3. Check success path, validation failure path, and error path coverage.
4. Report mismatches between API behavior and tests.

## Output format
- Findings (Critical, Warning, Note)
- Missing tests
- Suggested next action
```

Keep the file focused: one skill, one job, one output format.

## 11.3 Add a Concrete Invocation Example
Append this section to the same skill file:

```markdown
## Invocation example
#api-contract-review.skill.md `src/main/java/com/example/copilottraining/task/TaskController.java` `src/test/java/com/example/copilottraining/task/TaskControllerIntegrationTest.java`
```

This makes usage discoverable for new team members.

## 11.4 Use the Skill in Ask Mode
Open Copilot Chat in **Ask** mode and send:

> "#api-contract-review.skill.md Review `src/main/java/com/example/copilottraining/task/TaskController.java` and `src/test/java/com/example/copilottraining/task/TaskControllerIntegrationTest.java`. Return findings only."

Check whether the output follows your format. If not, tighten the skill wording.

## 11.5 Use the Skill in Agent Mode
Switch to **Agent** mode and send:

> "Apply #api-contract-review.skill.md to the task API files. If you find a missing integration test, implement exactly one focused test and run Maven tests."

Review all proposed changes before applying.

## 11.6 Improve the Skill (Version 2)
Revise the skill file to reduce ambiguity:
- Add explicit severity definitions
- Add "do not" rules (for example: do not suggest refactors unrelated to API contract)
- Add a deterministic output order

Then run the same Ask-mode prompt again and compare output quality.

## 11.7 Team Reuse Check
Ask another participant to run your skill unchanged.

Capture:
- What worked immediately
- Which part of the skill needed clarification
- One improvement for shared team usage

## 11.8 Debrief
- Which part of the skill definition most improved output quality: scope, steps, or format?
- Did the same skill behave differently in Ask vs Agent mode?
- What guardrails are needed before letting Agent mode apply code changes from a skill?
- Which second skill should your team create next?

