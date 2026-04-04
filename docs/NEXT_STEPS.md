# Current Status

- **Task**: LICENSE_SCREEN (License Viewer Implementation)
- **Branch**: feature/license-screen-v2
- **Phase**: 2 (Detailed Design)
- **Status**:
    - [x] Phase 1: Pre-preparation completed.
    - [x] Branch `feature/license-screen-v2` created and switched.
    - [ ] Phase 2: Detailed Design (Standard Flow) starting.

## TODO (Next Actions)

1. **Phase 2: Detailed Design**
    - [ ] 新規設計書 `docs/features/07_LICENSE_SCREEN_WEBVIEW.md` の作成（既存の `02`
      はメンテナンス終了とし、新規作成）。
    - [ ] ライセンスデータの定義（主要な OSS ライブラリのリストアップ）。
    - [ ] WebView による詳細表示ロジックの設計（`RepoDetailScreen` の仕組みを参考にする）。
    - [ ] ナビゲーションおよび遷移導線の定義。
    - [ ] ユーザー承認を得て、実装フェーズへ移行。

## Technical Memo

- 既存の `02_LICENSE_SCREEN.md` は変更せず、今回の改修内容は `07_LICENSE_SCREEN_WEBVIEW.md` に集約する。
- `05_REPO_DETAIL.md` で実装済みの WebView 関連コンポーネントの再利用性を検討する。
- ライブラリ一覧は `LazyColumn` で実装し、タップで WebView 画面へ遷移する構成を検討。
