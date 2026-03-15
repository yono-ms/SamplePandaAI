# 次回の作業予定 (Next Steps)

セッション再開時にスムーズに開発を継続するためのロードマップです。

## 現在の進捗

- [x] ログ基盤の導入 (SLF4J + Logback-Android)
- [x] DTO の自動生成設定 (OpenAPI Generator)
- [x] カスタムシリアライザーの実装 (`URI`, `OffsetDateTime`)
- [x] `GitHubApiService` の実装と `MockEngine` によるユニットテストの成功
- [x] **ドメイン層 (Domain Layer) の構築** (`GitHubRepo`, `GitHubRepository`)
- [x] **データ層 (Data Layer) の実装** (`GitHubRepositoryImpl` + Mapper)
- [x] **リポジトリの単体テスト完了** (`MockEngine` による正常系・異常系の検証)

## 次回のタスク

### 1. Flavor による接続環境の構築

- **Flavor 定義**: `app/build.gradle.kts` に `dev`, `prod` などの Flavor を追加。
- **BuildConfig の活用**: Flavor ごとに `BASE_URL` を切り替えられるように設定し、ハードコードを排除する。

### 2. プレゼンテーション層 (Presentation Layer) への接続

- **ViewModel の作成**: `GitHubRepository` を呼び出し、UI 状態（State）を管理。
- **Compose UI の実装**: `LazyColumn` を用いたリポジトリ一覧表示画面の作成。

### 3. モックサーバー対抗試験 (Optional/検証用)

- **実エンジンの検証**: `MockEngine` ではなく `OkHttp` エンジンを使用し、外部モックサーバー（WireMock
  等）との接続を Flavor 経由で検証する。

## 技術的メモ

- `GitHubRepositoryImplTest` において、`URI` および `OffsetDateTime` の `Contextual`
  シリアライザーの登録が必要であることを確認済み。
- リポジトリのマッピングロジックにおいて、`updatedAt` のパース失敗時のフォールバック処理を実装済み。
- `app/build.gradle.kts` の非推奨警告については継続監視。
