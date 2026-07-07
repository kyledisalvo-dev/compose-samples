# My Fork Setup Notes

This is **my fork** of Google's official Jetpack Compose samples.

## How this is set up

| What | Where |
|------|-------|
| My GitHub fork (`origin`) | https://github.com/kyledisalvo-dev/compose-samples |
| Local working copy | `~/kyledisalvo-dev/compose-samples` |
| Google's original (`upstream`) | https://github.com/android/compose-samples |

- I can make any changes I want locally, commit them, and `git push` — they go to **my fork**, not Google's repo.
- Google's original repo is connected as `upstream` (read-only for me) so I can pull in their updates.

## Everyday commands

```bash
# Save my changes to my GitHub fork
git add .
git commit -m "describe what I changed"
git push

# Pull in Google's latest updates (merges into my version)
git fetch upstream
git merge upstream/main
git push   # optional: send the merged updates up to my fork too
```

## Why a separate file instead of editing README.md?

If I edited Google's `README.md`, pulling their updates later could cause
merge conflicts. This standalone file stays out of their way.
