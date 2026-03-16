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
- [x] **開発環境の安定化 (AGP 8.5.2 / Kotlin 2.0.21)**
- [x] **Hilt による自動依存関係注入の導入**
- [x] **Flavor (dev/prod) による接続環境の構築**
- [x] **プレゼンテーション層の初期実装 (ViewModel + Compose UI)**
- [x] **ドメイン層への UseCase 導入 (`GetGitHubReposUseCase`)**
- [x] **UseCase の単体テスト完了 (MockK)**
- [x] **ViewModel の単体テスト完了 (Coroutines Test)**

## 次回のタスク

### 1. UI のブラッシュアップ

- **詳細表示・エラーハンドリングの強化**: テストで検証した `Error` 状態を、ユーザーに分かりやすく表示する
  UI の実装。
- **UI コンポーネントの共通化**: `RepoListComponents.kt` への抽出と整理。

### 2. インストゥルメントテスト (androidTest) の検討

- **Compose UI テスト**: 実際に画面にデータが表示されるか、ローディングが表示されるかの結合試験。

## 技術的メモ

- AGP 9.x 系プレビュー版での `srcDir` 問題を回避するため、AGP 8.5.2 安定版を維持すること。
- `kotlinx-coroutines-test` を使用する際は `runTest` と `advanceUntilIdle` を組み合わせて時間制御を行う。
- テスト用データは `src/test/resources` の JSON を活用し、一貫性を保つ。
