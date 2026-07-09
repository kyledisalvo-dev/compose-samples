# compose-samples (fork)

This is kyledisalvo-dev's fork of Google's android/compose-samples.
`origin` = the fork, `upstream` = Google's original. See FORK-NOTES.md for details.

## Start-of-session routine

At the start of each session in this repo, check whether Google has published
updates and offer to pull them in:

```bash
git fetch upstream
git log --oneline main..upstream/main   # what's new upstream
```

If there are new commits and the user wants them:

```bash
git merge upstream/main
git push
```

## Repo notes

- Each sample (JetNews, Jetchat, Jetsnack, Jetcaster, Reply, JetLagged) is its
  own Android Studio project — open the sample folder, never the repo root.
- Keep personal notes in FORK-NOTES.md / CLAUDE.md, not README.md, to avoid
  merge conflicts with upstream.
