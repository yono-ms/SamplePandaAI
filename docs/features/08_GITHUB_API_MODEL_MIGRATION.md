# 特徴設計: GitHub API モデルライブラリへの移行 (GITHUB_API_MODEL_MIGRATION)

## 1. ゴール (Goal)

現在、プロジェクト内で OpenAPI Generator を使用して独自に生成している GitHub API の DTO を廃止し、外部ライブラリ
`yono-ms/github-api-model` に移行する。

## 2. 外部ライブラリの調査結果 (最新版: 完全互換)

URL:
`https://github.com/yono-ms/github-api-model/blob/main/src/main/kotlin/io/github/yono_ms/model/models/Repository.kt`

### **Repository クラスの定義 (最新)**

- **パッケージ**: `io.github.yono_ms.model.models`
- **主要フィールド (すべて Non-nullable 化済み)**:
    - `id`: `kotlin.Long`
    - `name`: `kotlin.String`
    - `fullName`: `kotlin.String`
    - `stargazersCount`: `kotlin.Int`
    - `updatedAt`: `java.time.OffsetDateTime` (date-time 対応 / @Contextual)
- **結論**: `required` フィールドの追加により、現在のプロジェクトの DTO と **100% の型互換性**
  が確保された。

## 3. 影響範囲 (Scope)

- **依存関係**: GitHub Packages 経由で `jp.co.yono:github-api-model` を導入。
- **コード修正**:
    - `GitHubApiService`, `GitHubRepositoryImpl` のインポート先を `io.github.yono_ms.model.models`
      へ変更。
    - ロジック（nullチェック等）の追加修正は不要。
- **削除**: OpenAPI 生成タスク、`github_repos.yaml`、独自生成 DTO パッケージ。

## 4. 詳細設計 (Detailed Design)

### 4.1. 依存関係の追加

`settings.gradle.kts` に認証情報を追加。 `app/build.gradle.kts` に依存関係を追加。

### 4.2. データ構造の比較

| フィールド             | 現行 DTO (独自)      | 外部 DTO (最新)      | 一致状況     |
|:------------------|:-----------------|:-----------------|:---------|
| `id`              | `Long`           | `Long`           | **完全一致** |
| `stargazersCount` | `Int`            | `Int`            | **完全一致** |
| `updatedAt`       | `OffsetDateTime` | `OffsetDateTime` | **完全一致** |

### 4.3. 変換ロジックの修正

インポートパスの置換のみで対応可能。

## 5. テスト観点

- 外部ライブラリ DTO を用いた `GitHubRepoIntegrationTest` の全件パス。
- `OffsetDateTime` のデシリアライズ確認（既存のカスタムシリアライザーとの整合性）。
