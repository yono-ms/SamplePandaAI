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
- [x] **ライセンス情報画面の追加完了**
- [x] **国際化 (i18n) 対応完了**
- [x] **CI/CD 基盤の構築完了**
- [x] **リポジトリ詳細画面の実装完了**
- [x] **開発プロセスの強化: フェーズ 3 実装完了** (`docs/features/06_ENHANCE_DEV_FLOW.md`)

## 現在の作業フェーズ (Current Phase)

- **ターゲットタスク**: 開発プロセスの強化
- **作業フェーズ**: フェーズ 3: 実装とビルド確認 完了
- **現在のブランチ**: `feature/enhance-dev-flow`
- **ステータス**: ドキュメント群（DEVELOPMENT_FLOW, GEMINI, AGENTS, NEXT_STEPS）の更新を完了。

## TODO (今後のタスク)

- [ ] **開発プロセスの強化: フェーズ 4〜7 の実施**
  - [x] `DEVELOPMENT_FLOW.md` の各フェーズを新体系 (1-7) に移行し、チェックリストを導入。
  - [x] `GEMINI.md` にセッション開始プロトコル（ブランチ提案等）を反映。
  - [x] `NEXT_STEPS.md` に進捗表示の更新ルールを追記。
  - [ ] フェーズ 4: テストコード・レビュー (今回はドキュメントのみのため、内容の整合性確認)
- [ ] **既存機能의 改善**
  - [ ] 履歴画面からリポジトリ一覧画面への遷移時のデータ更新ロジックの最適化。
  - [ ] **ライセンス情報画面**: OSSライセンス情報を WebView で表示するように改善する。

## 技術的メモ

- 新しい開発フロー (1-7体系) への移行完了。以降、全作業でこのフローを適用する。
- 更新ルールの明文化: 各フェーズ完了許可後、AI は直ちに `NEXT_STEPS.md` を更新すること。
