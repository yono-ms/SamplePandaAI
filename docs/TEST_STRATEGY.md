# テスト戦略 (Test Strategy)

このドキュメントでは、SamplePandaAI プロジェクトにおける品質担保のためのテスト観点と、現在の実装状況を定義します。

## 1. テストの方針

- **自動テストの重視**: 手動テストを最小限にし、CI/CD で実行可能な自動テストを主体とする。
- **レイヤー別検証**: クリーンアーキテクチャの各レイヤーにおいて、適切な粒度のテストを実施する。
- **実データに近い検証**: Data層では `MockEngine` を用い、ネットワーク通信に近い形式でパースやシリアライズを検証する。
- **信頼性の担保**: AI エージェントが生成したコードは、必ずこれらのテストをパスすることを条件とする。
- **視覚的検証の活用**: Compose Preview を用い、UI のバリエーションを高速に検証する。
- **文字列リソースの整合性**: テストコードでも `strings.xml` を参照し、文言変更に対する堅牢性を確保する。

## 2. UI Layer の検証手法 (Compose Preview)

Compose を用いた UI 開発では、以下の設計規約を遵守することで、テスト容易性と視覚的確認の効率を最大化します。

### A. Stateful / Stateless の分離

- **Stateful Composable**: ViewModel に依存し、ナビゲーションやライフサイクルイベントをハンドルする。
- **Stateless Composable**: 描画に必要なデータ（State）とイベント（Lambda）のみを受け取る。
- **目的**: ロジックや DI に依存せず、UI コンポーネント単体での Preview 表示および UI テストを可能にする。

### B. Preview による UI 単体確認

- **役割**: 実機やエミュレータを起動せずに、UI のバリエーション（正常系、エラー、空状態、文字数超過など）を即座に視覚確認する「UI
  単体テスト」としての役割を果たす。
- **実装基準**: すべての画面 (Screen) および重要な共通コンポーネントに対して、主要な状態を網羅する
  Preview を作成する。

## 3. テスト観点と対応状況

### A. Data Layer (Remote / Repository / Local)

API 通信、JSON パース、ドメインモデルへの変換、およびローカル永続化を検証する。

| ID  | テスト観点                                       | 対応するテストコード (実在確認済み)          | ステータス |
|:----|:--------------------------------------------|:-----------------------------|:-----:|
| D-1 | 正常な JSON レスポンスが正しく DTO にデプロイされるか            | `GitHubApiServiceTest`       |   ✅   |
| D-2 | DTO からドメインモデルへのマッピングが正しいか                   | `GitHubRepositoryImplTest`   |   ✅   |
| D-3 | `URI` や `OffsetDateTime` のカスタムシリアライザーが動作するか | `GitHubRepositoryImplTest`   |   ✅   |
| D-4 | API が空配列を返した場合に空リストとして処理されるか                | `GitHubRepositoryImplTest`   |   ✅   |
| D-5 | ネットワークエラー発生時に適切に例外がスローされるか                  | `GitHubRepositoryImplTest`   |   ✅   |
| D-6 | 履歴データが `DataStore` に正しく保存・取得できるか            | `UserNameRepositoryImplTest` |   ✅   |
| D-7 | 履歴のソート順（新しい順）と最大件数(5件)が維持されるか               | `UserNameRepositoryImplTest` |   ✅   |

### B. Domain Layer (UseCase)

ビジネスロジック（データの加工・ソート・バリデーションなど）を検証する。

| ID  | テスト観点                                 | 対応するテストコード (実在確認済み)                 | ステータス |
|:----|:--------------------------------------|:------------------------------------|:-----:|
| U-1 | 取得したリポジトリ一覧がスター数の降順でソートされるか           | `GetGitHubReposUseCaseTest`         |   ✅   |
| U-2 | リポジトリが 0 件の場合に空リストを返すか                | `GetGitHubReposUseCaseTest`         |   ✅   |
| U-3 | ユーザー名のバリデーション（命名規則）が正しく機能するか          | `ValidateGitHubUserNameUseCaseTest` |   ✅   |
| U-4 | 与えられた URL が許可されたドメイン (GitHub) か判定できるか | `IsGitHubDomainUseCaseTest`         |   ✅   |

### C. UI Layer (ViewModel)

UI 状態（Loading, Success, Error）の遷移と、ユーザー操作によるイベントを検証する。

| ID  | テスト観点                                 | 対応するテストコード (実在確認済み)            | ステータス |
|:----|:--------------------------------------|:-------------------------------|:-----:|
| V-1 | 初期化時にデータ取得が開始され、Loading 状態になるか        | `GitHubRepoListViewModelTest`  |   ✅   |
| V-2 | データ取得成功時に Success 状態へ遷移し、データが保持されるか   | `GitHubRepoListViewModelTest`  |   ✅   |
| V-3 | データ取得失敗時に Error 状態へ遷移し、エラーメッセージを保持するか | `GitHubRepoListViewModelTest`  |   ✅   |
| V-4 | リトライ操作によって再取得が試行され、成功時に復帰するか          | `GitHubRepoListViewModelTest`  |   ✅   |
| V-5 | バリデーション結果に応じて ViewModel の状態が正しく更新されるか | `UserNameInputViewModelTest`   |   ✅   |
| V-6 | 履歴の削除アクションが UseCase に正しく伝播するか         | `UserNameHistoryViewModelTest` |   ✅   |

### D. UI Layer (Compose Screen)

Compose コンポーネントが UI 状態に応じて正しく描画されるかを検証する。

| ID  | テスト観点                                         | 対応するテストコード (実在確認済み)                          | ステータス |
|:----|:----------------------------------------------|:---------------------------------------------|:-----:|
| S-1 | Loading 状態時にインジケータが表示されるか                     | `RepoListScreenTest`                         |   ✅   |
| S-2 | Success 状態時にリポジトリ一覧が表示されるか                    | `RepoListScreenTest`                         |   ✅   |
| S-3 | Error 状態時にエラーメッセージが表示されるか                     | `UserNameInputScreenTest`                    |   ✅   |
| S-4 | 履歴画面で項目が表示され、削除操作が可能か                         | `UserNameHistoryScreenTest`                  |   ✅   |
| S-5 | OutlinedTextField が MD3 準拠のラベルとプレースホルダーを表示するか | `UserNameInputScreenTest`                    |   ✅   |
| S-6 | TopAppBar に戻るボタンが表示され、クリック可能か                 | `RepoListScreenTest`, `RepoDetailScreenTest` |   ✅   |
| S-7 | UI テストにおいて `strings.xml` のリソースから文字列を正しく検索できるか | `UserNameInputScreenTest`                    |   ✅   |
| S-8 | WebView 内での URL 遷移がドメインに応じて適切に制御されるか          | `GitHubWebViewClientTest`                    |   ✅   |
| S-9 | 詳細画面のタイトルが正しく表示されるか                           | `RepoDetailScreenTest`                       |   ✅   |

### E. Integration (結合テスト)

DI (Hilt) を用い、複数画面にまたがる一連の流れを検証する。

| ID  | テスト観点                                     | 対応するテストコード (実在確認済み)                               | ステータス |
|:----|:------------------------------------------|:--------------------------------------------------|:-----:|
| I-1 | アプリ起動後、実際の通信（Mock）を経てトップ画面にデータが表示されるか     | `GitHubRepoIntegrationTest`                       |   ✅   |
| I-2 | 名前入力 → 遷移 → 履歴保存 → 履歴から選択 という一連のフローが機能するか | `UserNameIntegrationTest`                         |   ✅   |
| I-3 | 画面遷移後に戻るボタンで前の画面に正しく復帰できるか                | `UserNameIntegrationTest`, `RepoDetailScreenTest` |   ✅   |
| I-4 | リポジトリ一覧から項目を選択した際、詳細画面へ遷移するか              | `RepoListScreenTest` (Click logic)                |   ✅   |

### F. Internationalization (国際化 - i18n)

多言語対応（リソース管理、レイアウト、フォント拡張）を検証する。

| ID     | テスト観点                                                                  | 検証手法 / 対応状況                    | ステータス |
|:-------|:-----------------------------------------------------------------------|:-------------------------------|:-----:|
| I18N-1 | すべての対応言語 (`ja`, `en`, `zh-rCN`, `de`, `ar`) で `strings.xml` が存在し不足がないか | 静的解析 / `03_I18N.md` 準拠         |   ✅   |
| I18N-2 | プレースホルダー (`%s`, `%d`) の数と型が各言語間で一致しているか                                | 静的解析 / 各 `strings.xml` 確認済み    |   ✅   |
| I18N-3 | **全言語プレビュー**: 全画面の Preview で全言語の表示が確認可能か                               | `@MultiLanguagePreview` 導入済み   |   ✅   |
| I18N-4 | **長文対応**: ドイツ語 (`de`) でボタンやラベルがはみ出さず適切に処理（改行/省略）されているか                 | `MultiLanguagePreview` による視覚確認 |   ✅   |
| I18N-5 | **RTL 対応**: アラビア語 (`ar`) で TopAppBar や ListItem の左右レイアウトが正しく反転しているか    | `MultiLanguagePreview` による視覚確認 |   ✅   |
| I18N-6 | **長文対応**: 拡大フォント設定時でもテキストが適切に表示されるか                                    | `Preview` (fontScale) による視覚確認  |   ✅   |
| I18N-7 | `androidTest` で `strings.xml` のリソースIDを使用して要素を特定しているか                   | `androidTest` 各コード (実在確認済み)    |   ✅   |

### G. CI/CD (GitHub Actions)

自動ビルドと自動テストによる品質の定常的な担保を検証する。

| ID   | テスト観点                                         | 検証手法 / 対応状況         | ステータス |
|:-----|:----------------------------------------------|:--------------------|:-----:|
| CI-1 | プルリクエスト作成時に自動的にビルドが実行され、成否が判定されるか             | GitHub Actions 構築済み |   ✅   |
| CI-2 | プルリクエスト作成時にユニットテストが自動実行され、すべてパスするか            | GitHub Actions 構築済み |   ✅   |
| CI-3 | ビルドまたはテストが失敗した場合に、PR のマージがブロックされるか (GitHub設定) | GitHub リポジトリ設定済み    |   ✅   |

## 4. 今後の課題・不足しているテスト

1. **インストゥルメントテストの CI 実行**:
    - CI 上での Android Emulator 実行環境（Firebase Test Lab 等の検討含む）。
2. **エッジケースの検証**:
    - DataStore のマイグレーション（将来的にスキーマ変更があった場合）。
3. **アクセシビリティテスト**:
   - TalkBack 等のスクリーンリーダー対応状況の定量的検証。
