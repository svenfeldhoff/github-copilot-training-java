# Module 10 - MCP Tool Integration

## Goal
Connect two MCP (Model Context Protocol) servers to Copilot so it can call your running Spring Boot API directly and interact with your GitHub repository — turning Copilot from a code generator into a tool that can observe, verify, and act on live systems.

## What is MCP?
MCP is an open protocol that lets AI models connect to external tools and data sources beyond the codebase. Instead of you copying API responses into Chat, Copilot calls the tool itself and reasons about the result. Tools appear as collapsible steps in the Agent mode chat window — just like file edits or terminal commands.

Two MCP servers are pre-configured for this project in `.github/copilot/mcp.json`:

| Server | What it does | Why it fits this training |
|--------|-------------|--------------------------|
| **fetch** | Makes HTTP requests and returns the response | Copilot can call `localhost:8080/api/tasks` and verify the live API contract against what the tests assert |
| **github** | Creates issues, reads PRs, queries the repo | Copilot can turn code-review findings (from Module 8/9) directly into tracked GitHub issues |

---

## Prerequisites
Before 10.1, verify these are installed:

```bash
# Python/uv (for the fetch server)
uvx --version

# Node.js (for the GitHub server)
node --version   # must be 18+
npx --version
```

If `uvx` is missing: install [uv](https://docs.astral.sh/uv/getting-started/installation/).
If `node` is missing: install [Node.js LTS](https://nodejs.org/).

---

## 10.1 Verify MCP Servers in Copilot Chat
Open **Copilot Chat** (right sidebar). In the Chat input area, look for a **gear icon** or **"Configure tools"** button. Click it.

You should see a dropdown listing:
- `fetch` (with status: Connected, Starting, or Error)
- `github` (with status: Connected, Starting, or Error)

If both show **Connected**, you're ready for 10.2.

If either shows **Error**, expand it and note the error message. Common errors:

| Error | Cause | Fix |
|-------|-------|-----|
| `invalid token: missing = param` | GitHub token not set or malformed | Run `$env:GITHUB_PERSONAL_ACCESS_TOKEN = "your_token"` (see 10.2) |
| `Request timed out` | `uvx` is downloading dependencies (first run) | Wait 3–5 minutes; click **retry** when ready |
| `Connection refused` on localhost | App not running | Start with `./mvnw spring-boot:run` |

> **If servers don't appear at all**: Close IntelliJ completely, restart it, and check again. IntelliJ reads from:
> ```
> c:\Users\<your-username>\AppData\Local\github-copilot\intellij\mcp.json
> ```

## 10.2 Set the GitHub Token
The GitHub MCP server requires a personal access token with `repo` and `issues:write` scope.

Set it as an environment variable — **never hardcode it in `mcp.json`**:

```powershell
# Windows PowerShell — add to your profile for persistence
$env:GITHUB_PERSONAL_ACCESS_TOKEN = "ghp_yourtoken"
```

Restart IntelliJ after setting the variable. Confirm the `github` server shows **Connected** in the MCP settings.

## 10.3 Start the Application and Verify Fetch Works
Start the app:

```bash
./mvnw spring-boot:run
```

Open Copilot Chat in **Agent** mode. Send:

> "Use the fetch tool to call GET http://localhost:8080/api/tasks and describe the response: HTTP status, response body structure, and whether it matches a list of task objects."

Expand the collapsed `fetch` step in the Chat window to see the raw HTTP call and response Copilot made. Confirm Copilot read the actual live response — not a guess based on the source code.

## 10.4 Probe the API Contract with Fetch
Still in Agent mode, send each of the following prompts **separately** and note what Copilot observes:

**Create a task:**
> "Use fetch to POST to http://localhost:8080/api/tasks with Content-Type: application/json and body `{\"title\": \"MCP test task\", \"description\": \"created via MCP\"}`. Report the HTTP status and every field in the response body."

**Trigger a 404:**
> "Use fetch to call GET http://localhost:8080/api/tasks/99999. Report the HTTP status and the exact response body. Is the error response a JSON object or plain text?"

**Trigger a validation failure:**
> "Use fetch to POST to http://localhost:8080/api/tasks with an empty body `{}`. Report the HTTP status and the response body. Does the error message identify which field failed validation?"

For each response, note whether the live API behaviour matches what the integration tests assert. Any gap is a real finding.

## 10.5 Compare Fetch Findings to Test Assertions
Based on 10.4, identify one API behaviour that your tests do **not** currently verify. Examples:
- The 404 response body is empty (tests only assert status code).
- The validation failure message does not name the failing field.
- The response includes an unexpected field.

Ask Copilot in Ask mode:

> "Based on the fetch findings, write one new integration test that covers [the gap you found]. Use MockMvc and assert the exact response body."

Run `./mvnw test` to confirm the new test passes.

## 10.6 Create a GitHub Issue from a Finding
Switch Copilot Chat to **Agent** mode (GitHub MCP requires Agent). Send:

> "Use the GitHub tool to create an issue in this repository titled: 'API contract gap: [describe your finding from 10.4]'. The body should describe the observed behaviour, the expected behaviour, and reference the relevant controller file."

Open your GitHub repository in the browser and confirm the issue was created with the correct content.

## 10.7 Link a Review Prompt to a Live Finding
Combine both MCP tools in one Agent mode session. Send:

> "Use fetch to call GET http://localhost:8080/api/tasks and POST http://localhost:8080/api/tasks with a missing required field. Then review the responses against the rules in #api-contract-review.prompt.md. For each Critical finding, create a GitHub issue using the GitHub tool."

Observe how Copilot chains the tools: fetch → review → issue creation. Expand each collapsed step to verify it called real HTTP endpoints and real GitHub API calls.

## 10.8 Debrief
- What did the fetch tool reveal about the running API that the tests did not cover?
- When would you trust Copilot's fetch-based findings without manually verifying them in a browser?
- What is the risk of giving Copilot write access to GitHub issues on a shared repository?
- Which other MCP server would be most useful for this project and why? (Examples: a database MCP connected to the app's data store, a Brave Search MCP for library research, a filesystem MCP for reading logs.)
