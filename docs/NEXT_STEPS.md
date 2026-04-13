# Current Status

- **Task**: GITHUB_API_MODEL_MIGRATION (Migrate to external GitHub API model library)
- **Branch**: feature/github-api-model-migration
- **Phase**: 2 (Detailed Design) - STARTING
- **Status**:
  - [x] Phase 1: Pre-preparation completed.
  - [ ] Phase 2: Detailed Design (Standard Flow) starting.
  - [ ] Phase 3: Implementation.
  - [ ] Phase 4: Test Code Review.
  - [ ] Phase 5: Test Execution.
  - [ ] Phase 6: External Review.
  - [ ] Phase 7: Final Documentation Sync.

## TODO (Next Actions)

1. **Detailed Design (Phase 2)**

- [ ] 既存コードの調査（DTO の使用箇所の特定）。
- [ ] `github-api-model` の依存関係追加方法の検討（JitPack 等）。
- [ ] 影響範囲の定義と設計書 (`docs/features/08_GITHUB_API_MODEL_MIGRATION.md`) の作成。

## Technical Memo

- `yono-ms/github-api-model` を調査し、現行の独自生成 DTO をこれに置き換える方針。
- 標準フロー (Standard Flow) を適用し、慎重に移行を進める。
- `app/build.gradle.kts` の `generateGitHubDto` タスクを最終的に削除することを目指す。
