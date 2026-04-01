# リポジトリ詳細画面の実装

## 1. ゴール

GitHub リポジトリの一覧から項目を選択した際、そのリポジトリのより詳細な情報を確認できる画面を実装する。
初期実装としては、WebView を用いて GitHub のプロジェクトページ（`htmlUrl`）を表示する。

## 2. 影響範囲

- `app/src/main/java/com/example/samplepandaai/domain/usecase/IsGitHubDomainUseCase.kt`: 新規作成。
- `app/src/main/java/com/example/samplepandaai/ui/features/GitHubWebViewClient.kt`: 新規作成。
- `app/src/main/java/com/example/samplepandaai/ui/navigation/Destinations.kt`: 新しい遷移先の追加。
- `app/src/main/java/com/example/samplepandaai/MainActivity.kt`: NavHost への画面追加。
- `app/src/main/java/com/example/samplepandaai/ui/features/RepoListScreen.kt`: リスト項目タップ時の遷移処理追加。
- `app/src/main/java/com/example/samplepandaai/ui/components/RepoListComponents.kt`: `RepoListItem`
  にクリックイベントを追加。

## 3. 新規クラス/関数の定義

### Domain Layer (Business Logic)

- `IsGitHubDomainUseCase`:
  - **責務**: 与えられた URL（文字列）が GitHub の許可されたドメイン（`github.com`
    およびそのサブドメイン）に属するかを判定する。
  - **理由**: アプリケーションのセキュリティポリシー（URLフィルタリング）をビジネスロジックとして
    Domain レイヤーに集約するため。
  - **実装**: Pure Kotlin で実装し、Android Framework (Uriクラス等) に依存せず、ホスト名の文字列解析のみで行う。

### Presentation Layer (UI)

- `GitHubWebViewClient`:
  - **責務**: `IsGitHubDomainUseCase` を使用して遷移先 URL を評価し、許可されたドメイン以外は
    `Intent.ACTION_VIEW` で外部ブラウザを起動する。
  - **実装**: `WebViewClient` を継承。UseCase を注入（DI）して使用する。

- `RepoDetailScreen`:
  - **責務**: 詳細画面のメイン Composable。`Scaffold` を持ち、内部で AndroidView (WebView) を表示する。

### Navigation

- `RepoDetail(val url: String, val title: String)`: 詳細画面への遷移定義。

## 4. UI 構成

- **TopAppBar**: リポジトリ名を表示し、戻るボタンを配置する。
- **Content**: WebView を使用して `htmlUrl` を全画面表示する。

## 5. テスト観点

### Domain Layer (Unit Test)

- `IsGitHubDomainUseCaseTest`:
  - `https://github.com/example/repo` -> true
  - `https://docs.github.com/` -> true
  - `https://google.com` -> false
  - 空文字や不正な形式の文字列 -> false

### Presentation Layer (Unit Test)

- `GitHubWebViewClientTest`:
  - UseCase が true を返した場合、`shouldOverrideUrlLoading` が false を返すこと。
  - UseCase が false を返した場合、`shouldOverrideUrlLoading` が true を返し、Intent が発行されること。

### UI Test (Instrumented Test)

- `RepoDetailScreenTest`:
  - タイトルが正しく表示されること。
  - 戻るボタンタップ時にコールバックが呼ばれること。

## 6. 実在性の確認

- `GitHubRepo`: 実在確認済み。`htmlUrl` プロパティを保持している。
- `RepoListScreen`: 実在確認済み。
- `RepoListItem`: `RepoListComponents.kt` 内に実在確認済み。
- `Destinations.kt`: 実在確認済み。
