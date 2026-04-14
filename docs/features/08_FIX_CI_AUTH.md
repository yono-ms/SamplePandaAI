# Feature 08: Fix CI Auth for GitHub Packages

## 1. ゴール (Goal)

GitHub Actions (CI) 環境において、外部リポジトリの GitHub Packages (`yono-ms/github-api-model`)
から依存関係を正常に解決できるようにする。

## 2. 影響範囲 (Scope)

- `.github/workflows/ci.yml`: 環境変数の設定追加。

## 3. 詳細設計 (Design)

### 現状の課題

- `settings.gradle.kts` は環境変数 `GITHUB_TOKEN` を参照して認証を行う設定になっている。
- 現在の `ci.yml` では `GITHUB_TOKEN` が渡されていないため、401 Unauthorized エラーが発生している。

### 修正内容

- `ci.yml` の各ジョブ (`build`, `unit-test`, `android-test`) に対して、環境変数 `GITHUB_TOKEN` を設定する。
- 値には `${{ secrets.GITHUB_TOKEN }}` を使用する。
    - ※ 相手先リポジトリが Public であれば、デフォルトのトークンで読み取り可能な可能性が高いため、まずはこれを試行する。

## 4. テスト観点 (Test Strategy)

- [ ] CI を再実行し、`checkDevDebugAarMetadata` タスク（および依存関係の解決）が成功すること。
- [ ] 401 Unauthorized エラーが解消されていること。

## 5. 考慮事項

- もしデフォルトの `GITHUB_TOKEN` で権限不足（403 または 401 が継続）となる場合は、PAT (Personal Access
  Token) を作成し、リポジトリの Secrets に `GPR_TOKEN` として登録した上で、`ci.yml` を修正する。
