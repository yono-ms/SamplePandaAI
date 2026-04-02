# リポジトリ詳細画面の実装

## 1. ゴール

GitHub リポジトリの一覧から項目を選択した際、そのリポジトリのより詳細な情報を確認できる画面を実装する。
初期実装としては、WebView を用いて GitHub のプロジェクトページ（`htmlUrl`）を表示する。

## 2. 影響範囲

- `app/src/main/java/com/example/samplepandaai/domain/usecase/IsGitHubDomainUseCase.kt`: 新規作成。
- `app/src/main/java/com/example/samplepandaai/ui/features/GitHubWebViewClient.kt`: 新規作成。
- `app/src/main/java/com/example/samplepandaai/ui/navigation/Destinations.kt`: 新しい遷移先の追加。
- `app/src/main/java/com/example/samplepandaai/MainActivity.kt`: NavHost への画面追加と Navigation
  引数のエンコード処理。
- `app/src/main/java/com/example/samplepandaai/ui/features/RepoDetailScreen.kt`: WebView
  のセキュリティ設定追加。

## 3. 実装クラス/関数の詳細

### Domain Layer (Business Logic)

- `IsGitHubDomainUseCase`:
  - **責務**: 与えられた URL が許可されたドメインかつ安全なスキームに属するかを判定する。
  - **ドメイン制限仕様**:
    - **スキーム**: `https://` のみを許可。`http://`, `intent:`, `file:`, `javascript:` 等はすべて拒否。
    - **ホスト**: `github.com` およびそのサブドメイン (`*.github.com`) のみを許可。
  - **実装**: Pure Kotlin で実装し、Android フレームワークへの依存を排除。

### Presentation Layer (UI)

- `GitHubWebViewClient`:
  - **責務**: `IsGitHubDomainUseCase` を使用して遷移先 URL を評価し、許可外の場合は外部ブラウザへ委譲する。

- `RepoDetailScreen`:
  - **WebView セキュリティ設定**:
    - `javaScriptEnabled = true`: GitHub ページの動的コンテンツ表示に不可欠なため有効化。
    - `allowFileAccess = false`: ローカルファイルへのアクセスを禁止。
    - `allowContentAccess = false`: コンテンツプロバイダへのアクセスを禁止。
    - その他、ファイル関連のアクセスを最小限に制限。

### Navigation

- **引数のエンコード**:
  - URL には `/`, `?`, `#` 等の Navigation ルート解析を妨げる文字が含まれるため、遷移前に
    `android.net.Uri.encode(url)` を行い、受信側で `Uri.decode(url)` を行う。

## 4. テスト実施内容

### Domain Layer (Unit Test)

- `IsGitHubDomainUseCaseTest`:
  - スキームチェック (`https` 必須) の検証。
  - 不正スキーム (`http`, `intent`, `file`) の拒否検証。
  - ドメイン判定の網羅的な検証。

### Presentation Layer (Unit Test)

- `GitHubWebViewClientTest`:
  - UseCase の判定結果に基づき、WebView 内で開くか外部ブラウザを起動するかの振り分けを検証。

## 5. 完了確認 (Final Check)

- [x] 設計に基づいた実装の完了
- [x] セキュリティ設定（スキーム・ファイルアクセス制限）の適用
- [x] Navigation 引数のエンコード対応
- [x] 全テストのパス
- [x] ドキュメントと最終実装の同期
