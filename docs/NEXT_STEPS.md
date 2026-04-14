# Current Status

- **Task**: GITHUB_API_MODEL_MIGRATION (Migrate to external GitHub API model library)
- **Branch**: feature/github-api-model-migration
- **Phase**: 2 (Detailed Design) - COMPLETED
- **Status**:
  - [x] Phase 1: Pre-preparation completed.
  - [x] Phase 2: Detailed Design (Standard Flow) completed.
  - [ ] Phase 3: Implementation.
  - [ ] Phase 4: Test Code Review.
  - [ ] Phase 5: Test Execution.
  - [ ] Phase 6: External Review.
  - [ ] Phase 7: Final Documentation Sync.

## TODO (Next Actions)

1. **Implementation (Phase 3)**

- [ ] `settings.gradle.kts` への GitHub Packages 認証設定の追加。
- [ ] `app/build.gradle.kts` への依存関係追加。
- [ ] `GitHubApiService.kt` および `GitHubRepositoryImpl.kt` の DTO インポート先を外部ライブラリへ変更。
- [ ] 独自生成 DTO 関連（`github_repos.yaml`, Gradle タスク）の削除。

## Technical Memo

- `yono-ms/github-api-model` の最新版において、`Long` 型の ID、`OffsetDateTime` 型の `updatedAt`
  、および主要フィールドの非 Null 化が確認されたため、既存ロジックを維持したまま移行可能。
- GitHub Packages の利用には `GITHUB_TOKEN` が必要。
- 標準フロー (Standard Flow) を適用し、各修正を確実に行う。
