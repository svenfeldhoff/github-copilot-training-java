# Module 0 - Prerequisites and Setup

## Goal
Verify your local environment is fully working before starting guided Copilot tasks — Java 25, IntelliJ, Copilot plugin, tests green, JaCoCo reporting, and Swagger visible.

## What You Are Setting Up
This project uses Java 25, Spring Boot 4, and Maven. All training modules assume the baseline is green: tests pass, coverage reports generate, and the API is reachable. You fix environment issues now, not mid-exercise.

---

## 0.1 Install and Configure IntelliJ
- Install **IntelliJ IDEA** (2025.2 or later, Ultimate or Community).
- Open **Settings → Plugins**, search for **GitHub Copilot**, install it, and sign in.
- Confirm the Copilot Chat panel appears in the right sidebar.

## 0.2 Configure JDK 25
Open **File → Project Structure → SDKs**, add JDK 25, and set it as the project SDK.

Verify in a terminal:

```bash
./mvnw -v
```

The output must show `Java version: 25`.

## 0.3 Run the Baseline Tests
```bash
./mvnw test
```

All tests must pass before continuing. If any fail, read the error message and fix the environment — do not proceed with failing tests.

## 0.4 Add JaCoCo with Copilot
Open Copilot Chat in **Ask** mode. Send:

> "Add the JaCoCo Maven plugin to pom.xml with prepare-agent bound to the test phase and report bound to verify. Do not change any other plugin configuration."

Review the generated `pom.xml` diff before applying it.

## 0.5 Generate the Coverage Report
```bash
./mvnw clean verify
```

Open `target/site/jacoco/index.html` in a browser. Confirm coverage percentages are visible for at least the `task` package.

## 0.6 Start the Application
```bash
./mvnw spring-boot:run
```

Open [http://localhost:8080/actuator/health](http://localhost:8080/actuator/health) and confirm the response is `{"status":"UP"}`.

## 0.7 Verify Swagger
Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

Confirm all existing `/api/tasks` endpoints are listed and expandable.

## 0.8 Explore the Copilot Context Files
Open the following files and read them before the first exercise module:
- `.github/README.md` — map of prompts, skills, agents, global instructions, and MCP config.
- `.github/copilot-instructions.md` — rules applied to every Copilot request.
- `.github/agents/myFixer.md` — custom agent profile for focused fixes.
- `.github/agents/myReviewer.md` — review-only agent profile for findings without code edits.
- `.github/prompts/` — reusable prompt files (used in Module 8).
- `.github/skills/` — reusable workflow files with stricter structure (used in Module 11).

Note which rules will affect code generation throughout the training.

## 0.9 Choose the Right AI Model
GitHub Copilot supports multiple models with different strengths and costs. Use the model selector in the Copilot Chat panel to switch models per session.

**Quick guidance:**
- **Free quota (0× multiplier):** GPT-4.1, GPT-5 mini — use for everyday completions.
- **Budget-friendly:** Claude Haiku 4.5, GPT-5.4 mini, Grok Code Fast 1 (0.25–0.33×).
- **Balanced (1×):** Claude Sonnet 4.6, GPT-5.3-Codex, Gemini 2.5 Pro — solid default for feature work.
- **Complex reasoning (3×):** Claude Opus 4.6 — reserve for architecture and deep analysis.

> 📖 Full model list, availability per plan, and up-to-date multipliers:
> **[docs.github.com — Supported AI models in GitHub Copilot](https://docs.github.com/en/copilot/reference/ai-models/supported-models)**
>
> See also **Module 1** (Copilot Actions Reference) for a detailed model comparison table.

## Debrief
- Which Copilot UI elements are available in IntelliJ (inline chat, Chat panel, completions)?
- What did the JaCoCo report reveal about the current coverage baseline?
- Which rules in `.github/copilot-instructions.md` are most likely to change Copilot's output quality?
- Which model would you pick for a quick refactor vs. a full architectural review, and why?
