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
- [x] Flavor (dev/prod) による接続環境の構築
- [x] プレゼンテーション層の初期実装 (ViewModel + Compose UI)
- [x] ドメイン層への UseCase 導入 (`GetGitHubReposUseCase`)
- [x] UseCase の単体テスト完了 (MockK)
- [x] ViewModel の単体テスト完了 (Coroutines Test)
- [x] UI のブラッシュアップ (Error表示、リトライ処理の検証)
- [x] インストゥルメントテスト (androidTest) の実装完了
- [x] テスト戦略ドキュメント (`docs/TEST_STRATEGY.md`) の作成
- [x] エラーハンドリングの強化とテスト追加
- [x] 設計・コスト検証ドキュメント (`docs/DESIGN_COST_VERIFICATION.md`) の作成
- [x] テストコードのリファクタリング（テストデータの外部ファイル化）完了
- [x] テストカバレッジの確認（Android Studio 標準機能の活用）完了

## 次回のタスク

- [ ] **アプリ本体の機能追加**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。
- [ ] **テスト・デリバリー基盤の検討 (中長期)**
  - [ ] GitHub Actions による自動テスト実行。
  - [ ] スクリーンショットテストの導入（Git管理コストと合わせて検討）。

## 技術的メモ

- テスト項目書は作成せず、テストコードを正として運用する。
- テスト観点については `docs/TEST_STRATEGY.md` で管理し、品質担保の指針を明確にする。
- カバレッジ確認は、Android Studio の **'Run with Coverage'** 機能を活用する（プロジェクト設定を汚さないため）。
- UIテストでは `HiltAndroidRule` と `ComposeTestRule` の順序 (`order`) に注意すること。
- ドキュメント作成時は `docs/PROJECT_STRUCTURE.md`, `docs/AGENTS.md`, `docs/GEMINI.md` の指針を遵守する。
