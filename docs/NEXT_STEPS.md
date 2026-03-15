# 次回の作業予定 (Next Steps)

セッション再開時にスムーズに開発を継続するためのロードマップです。

## 現在の進捗

- [x] ログ基盤の導入 (SLF4J + Logback-Android)
- [x] DTO の自動生成設定 (OpenAPI Generator)
- [x] カスタムシリアライザーの実装 (`URI`, `OffsetDateTime`)
- [x] `GitHubApiService` の実装と `MockEngine` によるユニットテストの成功

## 次回のタスク

### 1. ドメイン層 (Domain Layer) の構築

- **Domain Model の定義**: アプリ内で扱う純粋なデータクラスを `domain/model/` に作成。
- **Repository Interface の定義**: `domain/repository/` にインターフェースを定義。

### 2. データ層 (Data Layer) の実装

- **Repository Implementation**: `data/repository/` に `GitHubRepositoryImpl` を実装。
- **Mapper の作成**: 自動生成された DTO から Domain Model への変換ロジックを実装。
- **Repository のテスト**: `MockEngine` を使い、Mapper を含めたデータ取得フローを検証。

### 3. プレゼンテーション層 (Presentation Layer) への接続

- **ViewModel の作成**: Repository を呼び出し、UI 状態を管理。
- **Compose UI の実装**: リポジトリリストの表示画面を作成。

## 技術的メモ

- `app/build.gradle.kts` の `srcDir`/`buildDir` に関する非推奨警告は、現状「ビルドの安定性」のために許容している（詳細は
  `ARCHITECTURE_DESIGN.md` を参照）。
- ユニットテスト実行時は、引き続き DI フレームワークを使わず言語レベルでのコンストラクタ注入を優先する。
