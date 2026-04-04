# Current Status

- **Task**: LICENSE_SCREEN (License Viewer Implementation)
- **Branch**: feature/license-screen-v2
- **Phase**: 2 (Detailed Design) - COMPLETED
- **Status**:
    - [x] Phase 1: Pre-preparation completed.
    - [x] Branch `feature/license-screen-v2` created and switched.
  - [x] Phase 2: Detailed Design (Standard Flow) completed.
  - [x] ユーザー承認受領。

## TODO (Next Actions)

1. **Phase 3: Implementation**
    - [ ] `IsGitHubDomainUseCase` -> `IsSafeDomainUseCase` へのリファクタリング。
    - [ ] `GitHubWebViewClient` -> `SafeWebViewClient` へのリファクタリング。
    - [ ] `LicenseItem` および `LicenseDataProvider` の URL 化。
    - [ ] `LicenseScreen.kt` の修正（ダイアログ削除と Navigation 遷移の実装）。
    - [ ] `LicenseDetailScreen.kt` の新規実装。
    - [ ] `MainActivity.kt` への新ルート追加。
    - [ ] 全テストコードの修正・新規作成。

## Technical Memo

- 既存の `LicenseScreen` はリスト表示を維持し、詳細表示（ダイアログ）のみを WebView 画面への遷移に置き換える。
- `RepoDetailScreen` の WebView 実装を参考に、セキュアなブラウジングを共通化する。
- 既存テスト `LicenseScreenTest` 等の修正が必須。
