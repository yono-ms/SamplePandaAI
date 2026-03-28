# CI/CD 基盤の構築 (GitHub Actions)

## 1. ゴール

プルリクエスト (PR) 作成時に自動的にビルドとテストを実行し、品質が担保されていないコードが `main`
ブランチにマージされるのを防ぐ。

## 2. 影響範囲

- GitHub リポジトリ設定 (Branch Protection Rules)
- プロジェクト構造への新規ファイル追加 (`.github/workflows/ci.yml`)

## 3. GitHub Actions ワークフロー定義

### ファイルパス

`.github/workflows/ci.yml`

### ワークフロー概要

| 項目             | 内容                                                               |
|:---------------|:-----------------------------------------------------------------|
| **トリガー**       | `main` ブランチへの PR 作成/更新、および `main` への push                        |
| **実行環境**       | `ubuntu-latest` (Build/Unit Test), `macos-latest` (Android Test) |
| **Java バージョン** | 17 (zulu)                                                        |

### ジョブ詳細

1. **Build Job (`build`)**
    - 手順: チェックアウト -> JDKセットアップ -> Gradleキャッシュ復元 -> `assembleDebug` 実行。
    - 目的: コンパイルエラーがないことを確認する。

2. **Unit Test Job (`unit-test`)**
    - 手順: チェックアウト -> JDKセットアップ -> Gradleキャッシュ復元 -> `testDebugUnitTest` 実行。
    - 目的: ロジックの正しさを検証する。

3. **Android Test Job (`android-test`)**
    - 手順: チェックアウト -> JDKセットアップ -> エミュレータ起動 -> `connectedDebugAndroidTest` 実行。
    - 備考: `macos-latest` を使用することで、ハードウェアアクセラレーションを有効にし、エミュレータ実行を高速化する。

## 4. マージブロックルール (GitHub 設定)

実装完了後、GitHub のリポジトリ設定にて以下の「Branch protection rules」を有効にする。

- **対象ブランチ**: `main`
- **必須チェック項目 (Require status checks to pass before merging)**:
    - `build`
    - `unit-test`
    - `android-test`
- **その他の推奨設定**:
    - `Require a pull request before merging` (直接 push 禁止)
    - `Require conversations to be resolved before merging` (レビューコメント解決必須)

## 5. テスト観点 (CI 自体の検証)

| ID    | 検証内容                               | 確認方法                |
|:------|:-----------------------------------|:--------------------|
| CI-V1 | 構文エラーのある PR でビルドが失敗するか             | 意図的にエラーを混ぜた PR を作成  |
| CI-V2 | テストが失敗する PR でジョブが失敗するか             | 意図的にテストを落とした PR を作成 |
| CI-V3 | すべてパスした場合に GitHub 上でマージ可能（緑色）になるか  | 正常な PR を作成          |
| CI-V4 | Gradle キャッシュが効いて、2回目以降の実行時間が短縮されるか | Actions のログを確認      |

## 6. 実装上の注意点

- **API Key 等の秘匿情報**: 現時点では GitHub API の公開リポジトリを使用しているため、Secrets
  設定は不要。将来的に private なキーが必要になった場合は `GITHUB_TOKEN` または `Repository Secrets`
  を利用する。
- **エミュレータの安定性**: `macos-latest` はコストが高いため、実行頻度やテストケースの絞り込みを適宜検討する。
