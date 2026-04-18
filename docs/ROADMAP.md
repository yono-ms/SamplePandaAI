# プロジェクト・ロードマップ (TODO リスト)

このドキュメントでは、SamplePandaAI の今後の開発計画と未完了のタスクを管理します。

## 優先タスク

### 1. ライセンス情報画面の実装 (High) -> 完了間近

- **概要**: アプリで使用している OSS ライブラリのライセンス一覧を表示する。
- **詳細**:
  - [x] `docs/features/07_LICENSE_SCREEN_WEBVIEW.md` の設計 (WebView化)。
  - [x] `LicenseScreen` (Compose) および `LicenseDetailScreen` の作成。
  - [x] WebView によるライセンス詳細表示機能の実装。
  - [x] 許可ドメインの汎用化 (`IsSafeDomainUseCase`)。

## 今後の検討タスク

### 2. ライセンスリンクの正確性向上 (Medium)

- **概要**: 現在のライブラリ公式サイトへのリンクを、GitHub リポジトリ上の具体的なライセンスファイルへの直リンクに修正する。
- **詳細**:
  - [ ] `LicenseDataProvider.kt` の URL をリポジトリ内の `LICENSE` ファイル等へ更新。
  - [ ] `IsSafeDomainUseCase.kt` の許可ドメインリストの再整理。

### 3. ライセンス表示対象ライブラリの精査 (Low)

- **概要**: 現在表示している 6 つのライブラリ（Compose, Hilt, Ktor, Serialization, MockK, SLF4J）が適切か、`dependencies` に基づき見直す。
- **詳細**:
    - [ ] 実際にプロダクションで使用しているライブラリの抽出。
    - [ ] ユーザー向けの表示項目の決定。

### 4. リポジトリ詳細画面のブラッシュアップ (Medium)

- **概要**: `05_REPO_DETAIL.md` で実装した WebView 表示の改善。
- **詳細**:
    - [ ] 読み込み中インジケータ（LinearProgressIndicator）の追加。
    - [ ] エラー画面（オフライン時など）のハンドリング。
    - [ ] 「ブラウザで開く」アクションボタンの追加。

### 5. 技術的負債の解消 (Medium)

- **概要**: アーキテクチャ設計書で挙げられている課題への対応。
- **詳細**:
    - [ ] **OpenAPI Generator の警告解消**: Gradle 8.x 互換性への対応。
    - [ ] **DI の最適化**: Hilt モジュールの整理。
    - [ ] **ログ基盤の強化**: SLF4J/Logback の設定微調整。

### 6. UI/UX の向上 (Low)

- **概要**: ユーザー体験の向上。
- **詳細**:
    - [ ] **ダークモード対応**: Material Design 3 のカラースキーム最適化。
    - [ ] **アニメーション**: 画面遷移時やリスト表示時のアニメーション追加。
    - [ ] **オフラインキャッシュ**: 取得したリポジトリ一覧の DataStore または Room への保存。

## 完了済みタスク (アーカイブ)

- [x] 01: ユーザー名入力と履歴保存
- [x] 03: 多言語対応 (i18n)
- [x] 04: GitHub Actions による CI 設定
- [x] 05: リポジトリ詳細 (WebView) の基本実装
- [x] 06: 開発プロセスの強化 (GEMINI.md / DEVELOPMENT_FLOW.md)
