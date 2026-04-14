# Current Status

- **Task**: GITHUB_API_MODEL_MIGRATION (Migrate to external GitHub API model library)
- **Branch**: feature/github-api-model-migration
- **Phase**: 2 (Detailed Design - Lite Flow for CI Fix) - COMPLETED
- **Status**:
  - [x] Phase 1: Pre-preparation completed.
  - [x] Phase 2: Detailed Design (Lite Flow for CI Fix: `docs/features/08_FIX_CI_AUTH.md`)
    completed.
  - [ ] Phase 3: Implementation.
  - [ ] Phase 5: Test Execution.

## TODO (Next Actions)

1. **Implementation (Phase 3)**

- [ ] `.github/workflows/ci.yml` に `GITHUB_TOKEN` を追加。

2. **Test Execution (Phase 5)**

- [ ] CI を実行し、依存関係が解決されることを確認。

## Technical Memo

- `yono-ms/github-api-model` の最新版において、`Long` 型の ID、`OffsetDateTime` 型の `updatedAt`
  、および主要フィールドの非 Null 化が確認されたため、既存ロジックを維持したまま移行可能。
- GitHub Packages の利用には `GITHUB_TOKEN` が必要。
- 標準フロー (Standard Flow) を適用し、各修正を確実に行う。
