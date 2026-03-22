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
- [x] **開発フローの策定完了** (`docs/DEVELOPMENT_FLOW.md`)

## 現在の作業フェーズ (Current Phase)

- **ターゲットタスク**: ライセンス情報画面の追加
- **現在のブランチ**: なし (次回セッションで作成)
- **現在のフェーズ**: [ ] フェーズ 0: 前準備
- **ステータス**: `docs/DEVELOPMENT_FLOW.md` に基づく新フローを適用し、最初のタスクとして「ライセンス情報画面の追加」を開始する準備が完了。

## TODO (今後のタスク)

- [ ] **アプリ本体の次機能実装**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。
- [ ] **文字列リソースの課題対応**
  - [ ] テストコードにおける文字列判定の堅牢化（リソースIDやセマンティクスを利用した判定への改善）。
- [ ] **ライセンス情報画面の追加**
  - [ ] 使用している OSS ライブラリのライセンス一覧表示画面の実装。

## 技術的メモ

- 開発フローについては `docs/DEVELOPMENT_FLOW.md` を厳守すること。
- 各フェーズの完了（コミット）ごとにセッションをリセットすることを推奨する。
