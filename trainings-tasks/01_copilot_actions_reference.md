# Module 1 - Copilot Actions Reference

A quick-reference guide for all GitHub Copilot actions available in IntelliJ IDEA. Use this alongside any exercise module.

---

## Inline Chat (select code → right-click → Copilot, or `Alt+\`)

| Command | What it does | Best used for |
|---------|--------------|---------------|
| `/explain` | Describes the selected code: purpose, flow, edge cases | Understanding unfamiliar code before changing it |
| `/fix` | Detects and fixes a bug or failing test in the selection | Quick targeted patches |
| `/doc` | Adds a Javadoc comment to the selected method or class | Documenting before committing |
| `/tests` | Generates unit or integration tests for the selection | Test-first stubs, edge case coverage |
| `/simplify` | Rewrites the selection to be more concise | Cleaning up generated boilerplate |

> **Tip:** Always add context after the slash command.
> `/explain What are the failure modes of this method?` gets better output than `/explain` alone.

---

## Chat Panel Modes (Copilot Chat sidebar)

| Mode | Behaviour | When to use |
|------|-----------|-------------|
| **Ask** | Single-turn Q&A, no file edits | Explain concepts, look up patterns, review a snippet |
| **Plan** | Produces a step-by-step implementation plan, no edits | Before delegating complex multi-file work to Agent |
| **Agent** | Reads files, runs terminal commands, applies edits autonomously | Full feature implementation, test runs, multi-file refactors |

---

## Context References (type `#` or `@` in Chat)

| Reference | What it provides |
|-----------|-----------------|
| `#filename.java` | Attaches a specific file as context |
| `#foldername/` | Attaches all files in a folder |
| `#api-contract-review.prompt.md` | Loads a reusable prompt file from `.github/prompts/` |
| `@project` | Indexes the whole project for semantic search |
| `@github` | Access GitHub issues, PRs, and repository context |
| `@terminal` | Reads terminal output as context (last command result) |

---

## Slash Commands in Chat Panel

| Command | Effect |
|---------|--------|
| `/explain` | Explain selected or attached code |
| `/fix` | Propose a fix for a bug or test failure |
| `/tests` | Generate tests for attached code |
| `/simplify` | Simplify and reduce verbosity |
| `/doc` | Generate documentation |

---

## Agent Mode Tools (visible as collapsible steps in the Chat window)

When running in Agent mode, Copilot may use these tools automatically:

| Tool | What it does |
|------|-------------|
| **Read file** | Reads a source file to understand current state |
| **Edit file** | Applies code changes directly |
| **Run terminal** | Executes Maven/shell commands (`./mvnw test`, etc.) |
| **Search codebase** | Semantic search across all project files |
| **Create file** | Creates a new file in the project |

Expand each collapsed step in the Chat window to inspect what the agent read, changed, or ran.

---

## Custom Agent Profiles (`.github/agents/`)

Files in `.github/agents/` define named agents with a specific role, constraints, and model preference. Select them in the Chat panel agent picker. See `.github/agents/myFixer.md` for an example.

---

## Reusable Prompt Files (`.github/prompts/`)

Files in `.github/prompts/` with the `.prompt.md` extension are picked up automatically. Reference them with `#filename.prompt.md` in Chat to apply structured rules without retyping them each time. See Module 8 for hands-on usage.

