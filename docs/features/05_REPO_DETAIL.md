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

## 3. 実装クラス/関数の詳細

### Domain Layer (Business Logic)

- `IsGitHubDomainUseCase`:
  - **責務**: 与えられた URL が GitHub の許可されたドメイン（`github.com` およびそのサブドメイン）に属するかを判定する。
  - **実装**: Pure Kotlin で実装。`extractHost` によりホスト名を抽出し、ドメイン判定を行う。

### Presentation Layer (UI)

- `GitHubWebViewClient`:
  - **責務**: `IsGitHubDomainUseCase` を使用して遷移先 URL を評価。
  - **挙動**: 許可ドメイン内なら WebView で表示、それ以外は `Intent.ACTION_VIEW` で外部ブラウザを起動する。

- `RepoDetailScreen`:
  - **責務**: 詳細画面のメイン Composable。
  - **構成**: `TopAppBar` (タイトルと戻るボタン) + `AndroidView` (WebView)。
  - **プレビュー**: `LocalInspectionMode` を使用して WebView の描画エラーを回避。

### Navigation

- `RepoDetail(val url: String, val title: String)`: `kotlinx.serialization.Serializable`
  による型安全な遷移定義。

## 4. テスト実施内容

### Domain Layer (Unit Test)

- `IsGitHubDomainUseCaseTest`:
  - 正常系: `github.com`, `docs.github.com`, スキームなし URL の許可。
  - 異常系: `google.com`, `mygithub.com`, 空文字, null の拒否。

### Presentation Layer (Unit Test)

- `GitHubWebViewClientTest`:
  - MockK を使用し、UseCase の戻り値に応じて `context.startActivity` が呼ばれるか、または WebView
    内で読み込まれるか（`handleUrl` の戻り値）を検証。

### UI Test (Instrumented Test)

- `RepoDetailScreenTest`:
  - 画面タイトルの表示確認。
  - 戻るボタンタップ時のコールバック発火確認。

## 5. 完了確認 (Final Check)

- [x] 設計に基づいた実装の完了
- [x] 全テストのパス
- [x] Navigation への統合と実機/エミュレータでの動作確認
- [x] ドキュメントと最終実装の同期
