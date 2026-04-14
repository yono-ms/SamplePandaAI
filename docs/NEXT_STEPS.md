# Current Status

- **Task**: GITHUB_API_MODEL_MIGRATION (Migrate to external GitHub API model library)
- **Branch**: feature/github-api-model-migration
- **Phase**: 3 (Implementation) - COMPLETED
- **Status**:
  - [x] Phase 1: Pre-preparation completed.
  - [x] Phase 2: Detailed Design (Standard Flow) completed.
  - [x] Phase 3: Implementation.
  - [ ] Phase 4: Test Code Review.
  - [ ] Phase 5: Test Execution.
  - [ ] Phase 6: External Review.
  - [ ] Phase 7: Final Documentation Sync.

## TODO (Next Actions)

1. **Test Code Review (Phase 4)**

- [ ] 既存のテストコードが新しい DTO で動作するか、デシリアライズ設定の妥当性を確認。
- [ ] `OffsetDateTime` の変換ロジックが期待通り動作するか確認。

## Technical Memo

- `yono-ms/github-api-model` の最新版において、`Long` 型の ID、`OffsetDateTime` 型の `updatedAt`
  、および主要フィールドの非 Null 化が確認されたため、既存ロジックを維持したまま移行可能。
- GitHub Packages の利用には `GITHUB_TOKEN` が必要。
- 標準フロー (Standard Flow) を適用し、各修正を確実に行う。
