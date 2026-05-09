# Contributor notes for Claude Code (and other automated agents)

This file captures conventions that aren't enforced by tooling but
matter for the health of the codebase. Read it before opening a PR.

## Test coverage

**Never lower the JaCoCo line-coverage threshold (`jacoco.line.coverage.minimum` in `pom.xml`) just to make a build pass.**

If a change pulls more code into the covered bundle (for example, by
removing a class from `<excludes>`), the right response is to write
the tests that close the gap, not to drop the bar. Lowering the
threshold hides the regression and trains everyone who reads the
diff to expect the bar to move with the wind.

If you genuinely cannot reach the current threshold in a PR — for
instance, because a test would require infrastructure that's
out-of-scope (TestFX wiring, a CI environment change, an injectable
dialog factory) — leave the threshold alone, scope the PR down to
what you *can* cover, and call out the gap on the PR description so
the reviewer can decide. Don't ship a threshold drop quietly.

The only acceptable reasons to lower the threshold are:
- A reviewer (or repo owner) explicitly approves the new value in a
  comment on the PR.
- A separate issue/PR is dedicated to revising the policy itself.
