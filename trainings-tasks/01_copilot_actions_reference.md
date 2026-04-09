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

Files in `.github/agents/` define named agents with a specific role, constraints, and model preference. Select them in the Chat panel agent picker. See `.github/agents/myFixer.md` for a repair-focused agent and `.github/agents/myReviewer.md` for a findings-only review agent.

---

## Reusable Prompt Files (`.github/prompts/`)

Files in `.github/prompts/` with the `.prompt.md` extension are picked up automatically. Reference them with `#filename.prompt.md` in Chat to apply structured rules without retyping them each time. See Module 8 for hands-on usage.

---

## Skill Files (`.github/skills/`)

Files in `.github/skills/` are reusable workflow definitions. Use them when you need stricter inputs, ordered steps, and a predictable output format across repeated tasks. Reference them with `#filename` in Chat. See Module 11 for hands-on usage.

---

## AI Model Selection Guide

> **Full reference:** [Supported AI models in GitHub Copilot](https://docs.github.com/en/copilot/reference/ai-models/supported-models)
> Model availability and multipliers are subject to change — check the link above for the latest.

### When to Use Which Model

| Scenario | Recommended Model(s) | Why |
|---|---|---|
| Quick completions, low-cost everyday tasks | **GPT-4.1**, **GPT-5 mini**, **Raptor mini** | Multiplier 0× — counts against no premium quota |
| Budget-aware tasks with good quality | **Claude Haiku 4.5**, **GPT-5.4 mini**, **Gemini 3 Flash**, **Grok Code Fast 1** | Low multiplier (0.25–0.33×) |
| Standard feature work & code generation | **Claude Sonnet 4.6**, **GPT-5.3-Codex**, **Gemini 2.5 Pro** | Multiplier 1× — solid balance of cost and quality |
| Multi-step reasoning, architecture decisions | **Claude Opus 4.6** | Multiplier 3× — reserve for truly complex tasks |
| Avoid unless critical | **Claude Opus 4.6 (fast mode)** | Multiplier 30× — extremely expensive |

### Premium Request Multipliers

Each premium model request deducts from your plan's quota by its multiplier. `0×` models are effectively free.

| Model | Provider | Multiplier (paid) | Status |
|---|---|---|---|
| GPT-4.1 | OpenAI | **0×** | GA |
| GPT-5 mini | OpenAI | **0×** | GA |
| Raptor mini | Fine-tuned GPT-5 mini | **0×** | Preview |
| Grok Code Fast 1 | xAI | **0.25×** | GA |
| Claude Haiku 4.5 | Anthropic | **0.33×** | GA |
| GPT-5.4 mini | OpenAI | **0.33×** | GA |
| Gemini 3 Flash | Google | **0.33×** | Preview |
| Claude Sonnet 4 / 4.5 / 4.6 | Anthropic | **1×** | GA |
| GPT-5.2 / 5.3-Codex / 5.4 | OpenAI | **1×** | GA |
| Gemini 2.5 Pro / 3.1 Pro | Google | **1×** | GA |
| Claude Opus 4.5 / 4.6 | Anthropic | **3×** | GA |
| Claude Opus 4.6 (fast mode) | Anthropic | **30×** | Preview |

### JetBrains IDE — Switching Models

1. Open the **Copilot Chat** panel.
2. Click the **model selector** (dropdown next to the send button).
3. Choose the model for the current session. `Auto` lets Copilot pick based on availability.

> **Tip:** Use `Auto` for most tasks. Switch to a reasoning-heavy model (Opus, GPT-5.4) only when the task genuinely requires it — refactoring architecture, analysing complex bugs, or comparing multiple design options.

