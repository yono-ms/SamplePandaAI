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
- [x] **ライセンス情報画面の追加完了** (Compose Screen, Dialog, Navigation, Tests)
- [x] **国際化 (i18n) の基本実装完了** (Resource files, UI migration, Multipreview)
- [x] **国際化 (i18n) テストコード・レビュー完了 (Phase 3)**
- [x] **国際化 (i18n) 実機テスト・視覚的確認完了 (Phase 4)**

## 現在の作業フェーズ (Current Phase)

- **ターゲットタスク**: 国際化
- **作業フェーズ**: フェーズ 5: ドキュメント最新化・外部レビュー対応 (Documentation & External
  Review)
- **現在のブランチ**: `feature/i18n`
- **ステータス**: フェーズ 4 のテスト実施（全自動テスト・多言語プレビュー確認）が完了。これからフェーズ
  5 として、i18n 対応に関する設計ドキュメントの作成と、既存ドキュメントの同期、PR 作成準備を行う。

## TODO (今後のタスク)

- [ ] **国際化 (i18n) の対応**
  - [x] 現在の `strings.xml` の内容を整理し、ハードコードされている文字列がないか確認。
  - [x] 各言語リソース (`en`, `zh-rCN`, `de`, `ar`) の作成。
  - [x] UI コンポーネントの `stringResource` 化とプレースホルダー対応。
  - [x] 全言語対応 Multipreview の実装。
  - [x] テストコードのレビューと品質担保 (Phase 3)
  - [x] 実機テスト実行とレイアウト崩れの修正 (Phase 4)
  - [ ] **ドキュメント最新化と PR 作成 (Phase 5)**
- [ ] **アプリ本体の次機能実装**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。
- [ ] **既存機能の改善**
  - [ ] 履歴画面からリポジトリ一覧画面への遷移時のデータ更新ロジックの最適化。

## 技術的メモ

- 開発フローについては `docs/DEVELOPMENT_FLOW.md` を厳守すること。
- 各フェーズの完了（コミット）ごとにセッションをリセットすることを推奨する。
- 国際化にあたっては、Compose プレビューでの言語切り替え確認も併せて行う。
