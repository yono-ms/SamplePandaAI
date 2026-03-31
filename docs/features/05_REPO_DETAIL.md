# リポジトリ詳細画面の実装

## 1. ゴール

GitHub リポジトリの一覧から項目を選択した際、そのリポジトリのより詳細な情報を確認できる画面を実装する。
初期実装としては、WebView を用いて GitHub のプロジェクトページ（`htmlUrl`）を表示する。

## 2. 影響範囲

- `app/src/main/java/com/example/samplepandaai/ui/navigation/Destinations.kt`: 新しい遷移先の追加。
- `app/src/main/java/com/example/samplepandaai/MainActivity.kt`: NavHost への画面追加。
- `app/src/main/java/com/example/samplepandaai/ui/features/RepoListScreen.kt`: リスト項目タップ時の遷移処理追加。
- `app/src/main/java/com/example/samplepandaai/ui/components/RepoListComponents.kt`: `RepoListItem`
  にクリックイベントを追加。

## 3. 新規クラス/関数の定義

### Navigation

- `RepoDetail(val url: String, val title: String)`: 詳細画面への遷移定義。

### UI Components

- `RepoDetailScreen`: 詳細画面のメイン Composable。内部で AndroidView (WebView) を使用する。
- **WebView の制御**:
    - カスタム `WebViewClient` を実装。
    - `github.com` およびそのサブドメイン以外のドメインへの遷移が発生した場合、WebView
      内での読み込みをキャンセルし、`Intent.ACTION_VIEW` を用いて外部ブラウザに処理を委譲する。

## 4. UI 構成

- **TopAppBar**: リポジトリ名を表示し、戻るボタンを配置する。
- **Content**: WebView を使用して `htmlUrl` を全画面表示する。

## 5. テスト観点

- リポジトリ一覧から項目をタップした際、正しい URL を持って詳細画面に遷移すること。
- 詳細画面で戻るボタンを押した際、一覧画面に戻ること。
- WebView が指定された URL を読み込もうとすること（基本的な AndroidView の疎通確認）。
- **ドメイン制限の確認**: GitHub 以外のドメインへのリンクをタップした際、アプリ内の WebView
  が遷移せず、外部ブラウザを起動する Intent が発行されること。

## 6. 実在性の確認

- `GitHubRepo`: 実在確認済み。`htmlUrl` プロパティを保持している。
- `RepoListScreen`: 実在確認済み。
- `RepoListItem`: `RepoListComponents.kt` 内に実在確認済み。
- `Destinations.kt`: 実在確認済み。
