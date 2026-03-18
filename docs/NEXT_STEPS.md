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
- [x] **インストゥルメントテスト (androidTest) の実装完了**
- [x] **テスト戦略ドキュメント (`docs/TEST_STRATEGY.md`) の作成**
- [x] **エラーハンドリングの強化とテスト追加**
  - [x] 独自例外 `AppException` の導入とマッピングの実装
  - [x] API エラー（404等）およびネットワークエラーの Repository テスト追加
  - [x] ViewModel での例外捕捉と UI 状態への反映

## 次回のタスク

- [ ] **テストコードのリファクタリング（テストデータの外部ファイル化）**
  - [ ] `GitHubRepositoryImplTest` 等でハードコードされている JSON サンプルを、
    `app/src/test/resources/github_repos_success.json` 等の外部ファイル参照に切り替える。
  - [ ] `TestUtils.kt` にリソースファイルを読み込む共通関数を実装する。
- [ ] **カバレッジの可視化検討**
  - [ ] Jacoco 等の導入によるテスト網羅率の計測。

## 技術的メモ

- テスト項目書は作成せず、テストコードを正として運用する。
- テスト観点については `docs/TEST_STRATEGY.md` で管理し、品質担保の指針を明確にする。
- UIテストでは `HiltAndroidRule` と `ComposeTestRule` の順序 (`order`) に注意すること。
- ドキュメント作成時は `docs/PROJECT_STRUCTURE.md`, `docs/AGENTS.md`, `docs/GEMINI.md` の指針を遵守する。
- Ktor の `HttpClient` はデフォルトで 404 等を例外として投げないため、`GitHubApiService` で明示的にチェックし
  `ResponseException` を投げる構成としている。
