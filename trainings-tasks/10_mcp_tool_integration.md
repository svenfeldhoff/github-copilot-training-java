# Module 10 - MCP Tool Integration

## Goal
Connect two MCP (Model Context Protocol) servers to Copilot so it can call your running Spring Boot API directly and interact with your GitHub repository — turning Copilot from a code generator into a tool that can observe, verify, and act on live systems.

## What you will be able to do in this module

By the end of this module, Copilot will have access to two live tools:

### 🌐 fetch — call your running Spring Boot API
With the `fetch` MCP server configured, Copilot can make real HTTP requests to `localhost:8080` directly from the chat window. This means you can ask Copilot to:
- Call `GET /api/tasks` and verify the response structure matches what the tests expect
- Call `POST /api/tasks` with a valid or invalid body and see the exact HTTP status and response fields
- Trigger a `404` or `400` and inspect the real error response — without copying anything manually

This replaces the "copy response, paste into chat" workflow with a single Agent mode prompt.

### 🐙 github — create issues and interact with your repository
With the `github` MCP server configured, Copilot can interact with your GitHub repository directly. This means you can ask Copilot to:
- Create a GitHub issue from a live API finding — including the title, description, and a reference to the relevant source file
- Combine a fetch finding with a source-code review and automatically file the result as a tracked issue

This turns Copilot from a passive reviewer into an active participant in your development workflow.

---

## What is MCP?
MCP (**Model Context Protocol**) is an open standard that lets an AI model call external programs — tools — and reason about what those programs return, all within a single chat session.

Without MCP, Copilot only knows about your source code. You have to copy-paste API responses, database output, or GitHub data into the chat yourself.  
With MCP, Copilot calls the tool itself, sees the result, and continues reasoning — you just read the conversation.

### How it works in IntelliJ
When you switch Copilot Chat to **Agent** mode, IntelliJ starts the configured MCP servers as background processes. Each server exposes a set of **tool calls** (e.g. `fetch`, `create_issue`). When Copilot needs one, it calls the server, gets a response, and shows you both the call and the result as a collapsible step in the chat window — just like a file edit or a terminal command.

The servers for this project are declared in `.github/copilot/mcp.json`:
```
fetch server  → runs: uvx mcp-server-fetch    (Python, started by uv)
github server → runs: npx @modelcontextprotocol/server-github  (Node.js, started by npx)
```
IntelliJ reads that file and starts both processes automatically when you open a chat in Agent mode.

> **Two `mcp.json` files?** That is expected. `.github/copilot/mcp.json` is the **project-level source of truth** you commit to the repo. IntelliJ also maintains its own user-level copy at `%LOCALAPPDATA%\github-copilot\intellij\mcp.json`. If the two differ, use the project file as your reference and restart IntelliJ to let it refresh.

Two MCP servers are pre-configured for this project in `.github/copilot/mcp.json`:

| Server | What it does | Why it fits this training |
|--------|-------------|--------------------------|
| **fetch** | Makes real HTTP requests and returns the response | Copilot can call `localhost:8080/api/tasks` and verify the live API contract against what the tests assert |
| **github** | Creates issues, reads PRs, queries the repo | Copilot can turn code-review findings (from Module 8/9) directly into tracked GitHub issues |

---

## Prerequisites
Before 10.1, verify these are installed.

**What the commands below are doing:**

| Command | What it is | What it does here |
|---------|-----------|-------------------|
| `uvx` | Part of [uv](https://docs.astral.sh/uv/), a fast Python package runner | Runs `mcp-server-fetch` without a global install; on first run it downloads the package automatically |
| `node` | Node.js runtime | Required to run the GitHub MCP server |
| `npx` | Node.js package runner (bundled with Node) | Runs `@modelcontextprotocol/server-github` on demand, no global install needed |

Run these version checks in a PowerShell terminal:

```powershell
# Should print a version like 0.x.x — confirms uv/uvx is installed
uvx --version

# Should print v18.x or higher
node --version

# Should print a version number
npx --version
```

**Expected output example:**
```
uvx 0.6.14 (or similar)
v22.14.0
10.9.2
```

If `uvx` is missing: install [uv](https://docs.astral.sh/uv/getting-started/installation/).  
If `node` is missing: install [Node.js LTS](https://nodejs.org/).

> **First-run note:** The first time IntelliJ starts the `fetch` server, `uvx` will download `mcp-server-fetch` automatically. This can take 1–3 minutes. The server will show **Starting** or time out if the download is still in progress — just wait and retry.

---

## 10.1 Verify MCP Servers in Copilot Chat
Open **Copilot Chat** (right sidebar). Switch the mode picker to **Agent**. In the chat input area, look for a **gear icon** or **Configure tools** button. Click it.

You should see a dropdown listing:
- `fetch` (with status: Connected, Starting, or Error)
- `github` (with status: Connected, Starting, or Error)

If both show **Connected**, you're ready for 10.2.

If either shows **Error**, expand it and note the error message. Common errors:

| Error | Cause | Fix |
|-------|-------|-----|
| `Bad credentials` or `invalid token` | GitHub token not set or malformed | Set the token via `setx` and restart IntelliJ (see 10.2) |
| `Request timed out` | `uvx` is downloading dependencies (first run) | Wait 3–5 minutes; click **retry** when ready |
| `Connection refused` on localhost | App not running | Start with `.\mvnw.cmd spring-boot:run` |

> **If servers don't appear at all**: Close IntelliJ completely, restart it, and check again. IntelliJ reads from:
> ```
> c:\Users\<your-username>\AppData\Local\github-copilot\intellij\mcp.json
> ```
> Compare that file with `.github/copilot/mcp.json` in the repo. If they differ, use the project file as your source of truth and restart IntelliJ so the local copy can refresh.

## 10.2 Set the GitHub Token
The GitHub MCP server requires a **Personal Access Token (PAT)** to authenticate.

### Create the token
1. Go to [github.com → Settings → Developer settings → Fine-grained personal access tokens](https://github.com/settings/tokens?type=beta).
2. Click **Generate new token**.
3. Give it a name (e.g. `copilot-training-mcp`), set an expiration, and select your repository (`github-copilot-training-java`).
4. Under **Repository permissions**, grant at least:
   - **Issues** → Read and write
   - **Contents** → Read (needed for repo queries)
5. Click **Generate token** and copy the value (starts with `github_pat_…`).

### Store the token as an environment variable
**Never hardcode the token in `mcp.json`** — the config file is committed to the repo.

Instead, set a Windows environment variable and reference it with `${...}` syntax in the config:

```powershell
# Persist for all future sessions (requires IntelliJ restart to take effect)
setx GITHUB_PERSONAL_ACCESS_TOKEN "github_pat_yourtoken"

# Also set it in the current PowerShell session (for immediate terminal use)
$env:GITHUB_PERSONAL_ACCESS_TOKEN = "github_pat_yourtoken"
```

The project's `.github/copilot/mcp.json` already references the variable:

```json
{
  "env": {
    "GITHUB_PERSONAL_ACCESS_TOKEN": "${GITHUB_PERSONAL_ACCESS_TOKEN}"
  }
}
```

IntelliJ resolves `${GITHUB_PERSONAL_ACCESS_TOKEN}` at runtime from your system environment. This keeps the token on your machine — the config file is safe to commit.

### Verify the connection
1. **Restart IntelliJ** (so it picks up the new environment variable).
2. Open **Copilot Chat** → switch to **Agent** mode.
3. Click **Configure tools** (gear icon) and confirm the `github` server shows **Connected**.

> **Troubleshooting `Bad credentials`:**
> 1. Confirm the environment variable is set: run `echo $env:GITHUB_PERSONAL_ACCESS_TOKEN` in the IntelliJ terminal.
> 2. If it prints nothing, run the `setx` command above, then **fully restart IntelliJ**.
> 3. Check that the IntelliJ copy at `%LOCALAPPDATA%\github-copilot\intellij\mcp.json` matches `.github/copilot/mcp.json`. If they differ, restart IntelliJ to let it refresh.
> 4. In **Configure tools**, click **Restart** on the `github` server.

## 10.3 Start the Application and Verify Fetch Works
Start the app:

```powershell
.\mvnw.cmd spring-boot:run
```

Open Copilot Chat in **Agent** mode. Send:

> "Use the fetch tool to call GET http://localhost:8080/api/tasks and describe the response: HTTP status, response body structure, and whether it matches a list of task objects."

Expand the collapsed `fetch` step in the Chat window to see the raw HTTP call and response Copilot made. Confirm Copilot read the actual live response — not a guess based on the source code.

> **Important:** if Copilot answers without a visible `fetch` tool step, stop and treat that as a setup failure. The point of this module is tool use, not source-based guessing.

## 10.4 Probe the API Contract with Fetch
Still in Agent mode, send each of the following prompts **separately** and note what Copilot observes:

**Create a task:**
> "Use fetch to POST to http://localhost:8080/api/tasks with Content-Type: application/json and body `{"title":"MCP test task","description":"created via MCP","owner":"sven"}`. Report the HTTP status, the exact raw response body, and every field in the response body."

**Trigger a 404:**
> "Use fetch to call GET http://localhost:8080/api/tasks/11111111-1111-1111-1111-111111111111. Report the HTTP status and the exact response body. Is the error response a JSON object or plain text?"

**Trigger a validation failure:**
> "Use fetch to POST to http://localhost:8080/api/tasks with an empty body `{}`. Report the HTTP status and the response body. Does the error message identify which field failed validation?"

For each response, note whether the live API behavior matches what the integration tests assert. Any gap is a real finding.

Always expand the `fetch` step and verify the answer includes evidence from the live response. If there is no tool step, retry only after fixing the MCP setup.

Expected observations with the current implementation:
- The create call succeeds only if `owner` is included, because it is a required field.
- The 404 scenario requires a valid UUID that does not exist. A value like `99999` produces a `400 Bad Request` before the controller can look up a task.
- The validation failure currently returns a generic error body and does **not** identify the specific invalid field.

## 10.5 Compare Fetch Findings to Test Assertions
Based on 10.4, identify one API behavior that your tests do **not** currently verify. Examples:
- The 404 response body contains a JSON error object, but a test only asserts the status code.
- The validation failure message does not name the failing field.
- The response includes an unexpected field.

Ask Copilot in Ask mode:

> "Based on the fetch findings, write one new integration test that covers [the gap you found]. Use MockMvc and assert the exact response body."

Run `.\mvnw.cmd test` to confirm the new test passes.

## 10.6 Create a GitHub Issue from a Finding
Switch Copilot Chat to **Agent** mode (GitHub MCP requires Agent). Send:

> "Use the GitHub tool to create an issue in this repository titled: 'API contract gap: [describe your finding from 10.4]'. The body should describe the observed behaviour, the expected behaviour, and reference the relevant controller file."

Open your GitHub repository in the browser and confirm the issue was created with the correct content.

## 10.7 Trace a Live Finding Back to Source
This step combines tool evidence with a reusable prompt file. The fetch tool gives you the live behavior; `#api-contract-review.prompt.md` helps you review the controller and related code against the training standards.

Attach these files in chat before sending the prompt:
- `src/main/java/com/example/copilottraining/task/TaskController.java`
- `src/main/java/com/example/copilottraining/task/ApiExceptionHandler.java`
- `#api-contract-review.prompt.md`

Then, in one Agent mode session, send:

> "Use fetch to call GET http://localhost:8080/api/tasks and POST http://localhost:8080/api/tasks with a missing required field. First summarize the live API findings with raw evidence from the tool steps. Then apply #api-contract-review.prompt.md to the attached controller and exception-handler files. If you confirm a Critical gap between the live behavior, the source code, and the tests, create a GitHub issue using the GitHub tool."

Observe how Copilot chains the tools: fetch → source review → issue creation. Expand each collapsed step to verify it called real HTTP endpoints, used the attached prompt file, and made real GitHub API calls.

## 10.8 Debrief
- What did the fetch tool reveal about the running API that the tests did not cover?
- When would you trust Copilot's fetch-based findings without manually verifying them in a browser?
- What is the risk of giving Copilot write access to GitHub issues on a shared repository?
- Which other MCP server would be most useful for this project and why? (Examples: a database MCP connected to the app's data store, a Brave Search MCP for library research, a filesystem MCP for reading logs.)
