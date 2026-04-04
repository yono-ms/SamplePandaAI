# OSS ライセンス表示機能の改善 (WebView 化)

## 1. ゴール

既存の `LicenseScreen` におけるライセンス詳細表示を、ダイアログ形式から WebView 画面への遷移に変更する。
`RepoDetailScreen` で構築したセキュアな WebView 実装を汎用化（リファクタリング）して再利用し、メンテナンス性と一貫性のある
UI/UX を提供する。

## 2. 影響範囲とソースツリー更新案

### 2.1. 新規作成 (New)

- `app/src/main/java/com/example/samplepandaai/ui/features/license/LicenseDetailScreen.kt`:
    - ライセンス詳細を表示する WebView 画面。

### 2.2. リファクタリング (Rename / Move)

- `app/src/main/java/com/example/samplepandaai/domain/usecase/IsGitHubDomainUseCase.kt` ->
  `IsSafeDomainUseCase.kt`
- `app/src/main/java/com/example/samplepandaai/ui/features/GitHubWebViewClient.kt` ->
  `SafeWebViewClient.kt`

### 2.3. 既存コードの修正 (Modify)

- `app/src/main/java/com/example/samplepandaai/ui/features/license/LicenseScreen.kt`
- `app/src/main/java/com/example/samplepandaai/ui/features/license/LicenseDataProvider.kt`
- `app/src/main/java/com/example/samplepandaai/MainActivity.kt`

## 3. 実装の詳細

### 3.1. セキュリティ (Domain Layer)

- `IsSafeDomainUseCase` に許可ドメイン（github.com, www.apache.org, opensource.org, ktor.io,
  kotlinlang.org）を定義。
- HTTPS スキームのみを許可。

### 3.2. UI 構成 (Presentation Layer)

- `LicenseDetailScreen` で `SafeWebViewClient` を使用。
- ナビゲーションバーに戻るボタンを配置。

## 4. テスト観点

### 4.1. 既存テストの修正

- `LicenseScreenTest.kt`: ダイアログ検証から画面遷移検証（URL送信）へ。
- `IsSafeDomainUseCaseTest`: 許可ドメインの網羅的検証。
- `SafeWebViewClientTest`: UseCase との連携検証。

### 4.2. 新規テスト

- `LicenseDetailScreenTest.kt`: 正しい URL が WebView に渡されることの検証。

## 5. タスクリスト

- [ ] `IsSafeDomainUseCase` へのリファクタリング。
- [ ] `SafeWebViewClient` へのリファクタリング。
- [ ] `LicenseItem` データ構造更新 (`text` -> `url`)。
- [ ] `LicenseScreen.kt` の修正。
- [ ] `LicenseDetailScreen.kt` の新規作成。
- [ ] `MainActivity.kt` へのルート追加。
- [ ] 全テストコードの修正・作成。

---

## Appendix: Pull Request Draft

### 概要

既存の OSS ライセンス表示機能の詳細表示をダイアログから専用の WebView 画面へ移行し、WebView
制御ロジックを汎用化しました。

### 変更内容

- **新規**: `LicenseDetailScreen.kt` (WebView詳細画面)
- **リファクタリング**: `IsGitHubDomainUseCase` -> `IsSafeDomainUseCase`, `GitHubWebViewClient` ->
  `SafeWebViewClient`
- **修正**: `LicenseScreen.kt` (ダイアログ削除、遷移実装), `LicenseDataProvider.kt` (URL化)
- **テスト**: `LicenseScreenTest` の更新、`LicenseDetailScreenTest` の新規追加。

### 影響範囲

- `ui.features.license`, `domain.usecase`, `ui.features`, `MainActivity.kt`
- `docs/PROJECT_STRUCTURE.md`, `docs/TEST_STRATEGY.md`
