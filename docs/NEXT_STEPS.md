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
  - [x] 設計ドキュメント作成 (`docs/features/01_USER_NAME_INPUT.md`)
  - [x] Type-safe Navigation への移行 (Serializable object/class)
  - [x] バリデーションロジックの UseCase 移行 (`ValidateGitHubUserNameUseCase`)
  - [x] `UserNameRepository` (DataStore) による履歴管理の実装
  - [x] 2画面構成 (`UserNameInputScreen`, `UserNameHistoryScreen`) の実装
  - [x] 両画面への Compose Preview 導入 (Stateless 化)
  - [x] 最終テスト実行・検証完了 (ユニットテスト & 結合テスト)
  - [x] **プロジェクト全体のリファクタリング（機能追加 1 完了に伴う整理）完了**
    - [x] TextField/OutlinedTextField の MD3 デザイン準拠 (Label 引数の使用)
    - [x] 文字列リソースの整理 (`strings.xml` への移行)
    - [x] RepoListScreen への戻るボタンの実装とテスト整合
  - [x] 実機での動作確認完了

## 次回のタスク

- [ ] **開発フローの策定 (概要)**
  - [ ] 開発の全体フロー、ブランチ戦略、および AI エージェントとの協調ルールのドラフト作成。
  - [ ] テスト実施に関する指針（失敗時のワークフロー等）の包含。

## TODO (今後のタスク)

- [ ] **文字列リソースの課題対応**
  - [ ] テストコードにおける文字列判定の堅牢化（ハードコードを避け、リソースIDやセマンティクスを利用した判定への改善）。
  - [ ] 多言語対応（国際化 / i18n）の準備。
- [ ] **アプリ本体の次機能実装**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。
- [ ] **ライセンス情報画面の追加**
  - [ ] 使用している OSS ライブラリのライセンス一覧表示画面の実装。

## 技術的メモ

- 画面遷移は Android 公式マニュアル推奨の Type-safe Navigation を使用すること。
- バリデーション等のビジネスルールは ViewModel ではなく UseCase に集約すること。
- テスト観点については `docs/TEST_STRATEGY.md` で管理し、品質担保の指針（Preview による視覚的検証含む）を明確にする。
