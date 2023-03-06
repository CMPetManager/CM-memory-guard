### backend service to serve web photo album project

## branch strategy:
- main branch for production
- dev-release for release testing before rollout to prod
- dev branch for current developing
- for feature developing create branch from dev branch locally
- for new feature create branch - feature/{task-number}
- after finishing task, make push to repo and create merge request
- for bug fix on test-stage, create branch from dev-release
- for bug fix create branch - bugfix/{task-number}
- for bug fix on prod create branch from main - hotfix/{task-number}
- create short but self-defining commit messages, but there is always place for fun)
