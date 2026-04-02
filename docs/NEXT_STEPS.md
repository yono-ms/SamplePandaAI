# 次回の作業予定 (Next Steps)

セッション再開時にスムーズに開発を継続するためのロードマップです。

## 現在の進捗

- [x] ログ基盤の導入 (SLF4J + Logback-Android)
- [x] DTO の自動生成設定 (OpenAPI Generator)
- [x] カスタムシリアライザーの実装 (`URI`, `OffsetDateTime`)
- [x] `GitHubApiService` の実装と `MockEngine` によるユニットテストの成功
- [x] ドメイン層 (Domain Layer) の構築 (`GitHubRepo`, `GitHubRepository`)
- [x] データ層 (Data Layer) の実装 (`GitHubRepositoryImpl` + Mapper)
- [x] リポジトリの単体テスト完了
- [x] 開発環境の安定化 (AGP 8.5.2 / Kotlin 2.0.21)
- [x] Hilt による自動依存関係注入の導入
- [x] Flavor (dev/prod) による接続環境 of 構築
- [x] プレゼンテーション層の初期実装 (ViewModel + Compose UI)
- [x] ドメイン層への UseCase 導入
- [x] ViewModel の単体テスト完了 (Coroutines Test)
- [x] UI のブラッシュアップ (Error表示、リトライ処理の検証)
- [x] インストゥルメントテスト (androidTest) の実装完了
- [x] テスト戦略ドキュメント (`docs/TEST_STRATEGY.md`) の作成
- [x] エラーハンドリングの強化とテスト追加
- [x] 設計・コスト検証ドキュメント (`docs/DESIGN_COST_VERIFICATION.md`) の作成
- [x] テストコードのリファクタリング（テストデータの外部ファイル化）完了
- [x] テストカバレッジの確認（Android Studio 標準機能の活用）完了
- [x] **機能追加 1: ユーザー名入力画面のコア実装完了**
- [x] **開発フローの策定完了** (`docs/DEVELOPMENT_FLOW.md`)
- [x] **ライセンス情報画面の追加完了** (Compose Screen, Dialog, Navigation, Tests)
- [x] **国際化 (i18n) 対応完了** (Resource files, UI migration, Multipreview, Tests)
- [x] **CI/CD 基盤の構築完了**
- [x] **リポジトリ詳細画面の実装: フェーズ 1 (詳細設計) 完了** (`docs/features/05_REPO_DETAIL.md`)
- [x] **リポジトリ詳細画面の実装: フェーズ 2 (実装) 完了**
- [x] **リポジトリ詳細画面の実装: フェーズ 3 (テストコード・レビュー) 完了**
- [x] **リポジトリ詳細画面の実装: フェーズ 4 (テスト実施) 完了**
- [x] **リポジトリ詳細画面の実装: フェーズ 5 (ドキュメント最新化) 完了**

## 現在の作業フェーズ (Current Phase)

- **ターゲットタスク**: リポジトリ詳細画面の実装
- **作業フェーズ**: フェーズ 5: 完了
- **現在のブランチ**: `feature/repository-detail`
- **ステータス**: すべてのフェーズを完了。

## TODO (今後のタスク)

- [ ] **開発プロセスの強化**
  - [ ] 他のフェーズ（フェーズ1, 3, 4, 5）のチェックリストについても `DEVELOPMENT_FLOW.md` の見直しを行う。
- [ ] **既存機能の改善**
  - [ ] 履歴画面からリポジトリ一覧画面への遷移時のデータ更新ロジックの最適化。
  - [ ] **ライセンス情報画面**: OSSライセンス情報を WebView で表示するように改善する。

## 技術的メモ

- 開発フローについては `docs/DEVELOPMENT_FLOW.md` を厳守すること。
- WebView 実装時は `WebViewClient.shouldOverrideUrlLoading` を使用してドメイン制限を行う。
- 外部ブラウザへの遷移は `Intent.ACTION_VIEW` を使用する。
- Compose Preview における `WebView` の描画エラーは `LocalInspectionMode` による条件分岐で回避する。
