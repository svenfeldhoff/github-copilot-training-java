# Module 4 - Version Control and Review

## Goal
Use Copilot in Git workflows — documentation, commit messages, and diff review — while keeping every commit independently safe to revert.

## Why This Matters
Copilot can accelerate every stage of a commit, but it cannot judge risk on your behalf. A generated commit message that says "update code" is worse than writing nothing. A generated diff review that misses a removed null check costs you in production. This module builds habits that make AI-assisted commits trustworthy.

---

## 4.1 Document All Changed Public Methods
Before committing anything, select each public method you added or changed. Open inline chat and send:

```
/doc Add Javadoc for this method including @param, @return, and @throws. Be specific about what the method does — no generic descriptions.
```

Accept only accurate Javadoc. Rewrite anything that says "processes the request" or uses other placeholder language.

## 4.2 Draft a Commit Message
Stage your changes in the Git panel. Open Copilot Chat in **Ask** mode and send:

> "Draft a commit message for this staged diff. The subject line must explain *why* the change exists, not just what changed. Max 72 characters for the subject line."

Read the draft. Rewrite the subject line if it describes mechanics (`Add owner field`) instead of intent (`Require owner on all tasks to support team-based filtering`).

## 4.3 Request a Risk-Focused Diff Review
In Chat panel **Ask** mode, attach the diff and send:

> "#git-diff Review this diff for: behavioral regressions, missing or weakened tests, API contract changes that could break existing clients, and constructor injection violations. Order findings by risk."

Read every finding before acting on any of them.

## 4.4 Validate Each Finding Manually
For each finding from 4.3, open the referenced file and confirm whether the issue exists. Categorise each as:
- ✅ Real — apply the fix.
- ⚠️ Partial — the risk is real but the suggested fix is wrong; correct it yourself.
- ❌ False positive — the code is correct; note why.

Do not apply fixes directly from the review without reading the changed lines.

## 4.5 Split Into Two Commits
Separate your changes into exactly two commits:
1. **Feature commit** — production code only (controller, service, DTOs).
2. **Test commit** — test files only.

Ask Copilot for the exact `git` commands if needed. Verify the split with `git log --oneline`.

## 4.6 Verify Independence
For each commit, confirm it could be reverted cleanly:

> "If I revert the feature commit but keep the test commit, would the build still compile? If I revert only the test commit, would the feature still work?"

Ask Copilot to check — then verify the answer yourself by looking at the dependency direction between commits.

## Debrief
- Did the generated commit message explain *why* on the first attempt, or did you have to rewrite it?
- How many findings from the diff review were false positives vs real issues?
- What is one rule you would add to `.github/copilot-instructions.md` to catch a finding that Copilot missed?
- Could each of your two commits be reverted independently without breaking the build?
