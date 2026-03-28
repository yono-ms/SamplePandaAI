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
- [x] **国際化 (i18n) 対応完了** (Resource files, UI migration, Multipreview, Tests)
- [x] **CI/CD 基盤の構築: フェーズ 1 (詳細設計) 完了** (`docs/features/04_CI_SETUP.md`)
- [x] **CI/CD 基盤の構築: フェーズ 2 (実装) 完了** (`.github/workflows/ci.yml`)

## 現在の作業フェーズ (Current Phase)

- **ターゲットタスク**: CI/CD 基盤の構築
- **作業フェーズ**: フェーズ 4: テスト（GitHub Actions での動作確認）
- **現在のブランチ**: `feature/ci-setup`
- **ステータス**: `.github/workflows/ci.yml` の作成およびローカルでのビルドタスク検証が完了。GitHub
  への push 後に Actions が正常に動作するかを確認する。

## TODO (今後のタスク)

- [ ] **GitHub Actions による CI 構築 (Phase 6 計画分)**
  - [x] PR 作成時および `main` ブランチへのプッシュ時の自動ビルド設計 (Phase 1)。
  - [x] `.github/workflows/ci.yml` の実装 (Phase 2)。
  - [ ] ビルド・テストの自動実行確認 (Phase 4)。
  - [ ] ビルド失敗時にマージをブロックする仕組みの導入（GitHubリポジトリ設定のガイダンス）。
- [ ] **アプリ本体の次機能実装**
  - [ ] リポジトリ詳細画面の実装。
  - [ ] WebView によるリポジトリ表示機能。
- [ ] **既存機能の改善**
  - [ ] 履歴画面からリポジトリ一覧画面への遷移時のデータ更新ロジックの最適化。

## 技術的メモ

- 開発フローについては `docs/DEVELOPMENT_FLOW.md` を厳守すること。
- CI 設定ファイルは `.github/workflows/` 配下に配置する。
- Android のビルドには `Setup Java` および Gradle Action を使用する。
- Flavor 設定 (dev/prod) に対応するため、特定の Gradle Task (`testDevDebugUnitTest` 等) を指定している。
