# Copilot Assets in This Repository

This folder contains the reusable Copilot assets used throughout the training.

Use this guide as the quick orientation map before working through Modules 8–11.

---

## Which artifact should I use?

| Artifact | Location | Applied how | Best used for |
|---|---|---|---|
| Global instructions | `.github/copilot-instructions.md` | Automatically on every Copilot request | Project-wide coding and testing rules |
| Prompt files | `.github/prompts/*.prompt.md` | Attach on demand with `#filename` | Reusable guidance for a single review or generation task |
| Skill files | `.github/skills/*.skill.md` | Invoke on demand with `#filename` | Repeatable workflows with strict inputs, steps, and output format |
| Agent profiles | `.github/agents/*.md` | Select in the Chat agent picker | Specialized execution style and operating constraints |
| MCP config | `.github/copilot/mcp.json` | Loaded by Copilot Agent mode | Live tool access such as HTTP calls and GitHub actions |

---

## 1. Global instructions

File: `.github/copilot-instructions.md`

These rules are always on. They define the baseline expectations for the whole repository.

Examples in this project:
- Use Java 25 where it improves readability
- Keep controller -> service separation
- Add or update tests for behavior changes
- Return JSON objects/arrays for business API responses

Use this file for rules you want Copilot to follow in every session.

---

## 2. Prompt files

Folder: `.github/prompts/`

Prompt files are lightweight, reusable instruction sets that you attach only when needed.

Use a prompt file when you want:
- a reusable checklist
- a reusable review lens
- a reusable test scaffold
- structured guidance for one request

Examples in this project:
- `api-contract-review.prompt.md`
- `integration-test-scaffold.prompt.md`

Think of a prompt file as: **"apply this reusable instruction set to my current request."**

---

## 3. Skill files

Folder: `.github/skills/`

Skills are stricter than ordinary prompt files. A skill should describe a repeatable workflow, not just a reusable instruction.

Use a skill when you want:
- a clearly scoped recurring task
- required inputs
- ordered steps
- explicit severity or decision rules
- deterministic output format
- repeatable team usage across Ask and Agent mode

Example in this project:
- `api-contract-review.skill.md`

Think of a skill as: **"run this reusable workflow and return the result in a predictable format."**

---

## 4. Agent profiles

Folder: `.github/agents/`

Agent profiles define how Copilot should behave while working autonomously.

Use an agent profile when you want to control things like:
- scope of edits
- acceptable risk level
- stop conditions
- review vs implementation behavior
- expected output structure

Examples in this project:
- `myFixer.md` - small, test-backed repairs
- `myReviewer.md` - findings-only review with no code edits

Think of an agent profile as: **"work like this kind of specialist."**

---

## 5. MCP configuration

File: `.github/copilot/mcp.json`

This file declares external tools Copilot can use in Agent mode.

Current training tools:
- `fetch` - call the running API and inspect live responses
- `github` - create issues and query the repository

Use MCP when you need evidence from a running system or want Copilot to interact with external tools instead of guessing from source alone.

> Safety note: some tools are read-oriented (`fetch`), while others can write to external systems (`github`). Always review write-capable actions carefully.

---

## Suggested decision guide

Use this quick rule of thumb:

- Need a repo-wide default rule? -> `copilot-instructions.md`
- Need a reusable one-request helper? -> prompt file
- Need a repeatable team workflow? -> skill file
- Need autonomous behavior with a specific operating style? -> agent profile
- Need live verification or external actions? -> MCP tool

---

## How this maps to the training modules

- **Module 8** -> Prompt files and reusable playbooks
- **Module 9** -> Custom agents and capstone workflow
- **Module 10** -> MCP tool integration
- **Module 11** -> Skills as reusable workflows

Read this file before starting those modules if the differences feel blurry.

