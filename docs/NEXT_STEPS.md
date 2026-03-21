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

## 次回のタスク

- [ ] **機能追加 1 の最終テスト実行・検証**
  - [ ] 全ユニットテスト (`test`) の実行とパス確認。
  - [ ] 画面遷移・履歴保存の結合テスト (`Integration Test`) の実行とパス確認。
- [ ] **プロジェクト全体のリファクタリング（機能追加 1 完了に伴う整理）**
  - [ ] **TextField/OutlinedTextField のデザインガイドライン確認**: 現在のラベル外部配置が Material
    Design 3 の標準的なエラー表示方法（Supporting Text 等）と照らして妥当か検証し、必要に応じて修正。
  - [ ] **文字列リソースの整理**: UI 上の固定文言やエラーメッセージを `strings.xml` または `object`
    定義に集約し、ハードコードを排除。
- [ ] **アプリ本体の次機能実装**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。

## 技術的メモ

- 画面遷移は Android 公式マニュアル推奨の Type-safe Navigation を使用すること。
- バリデーション等のビジネスルールは ViewModel ではなく UseCase に集約すること。
- テスト観点については `docs/TEST_STRATEGY.md` で管理し、品質担保の指針（Preview による視覚的検証含む）を明確にする。
