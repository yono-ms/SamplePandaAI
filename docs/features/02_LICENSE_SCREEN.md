# 詳細設計：ライセンス情報画面

## 1. ゴール

- アプリで使用している主要なオープンソースライブラリのライセンス情報をユーザーが確認できるようにする。

## 2. 影響範囲

- **UI (ui/features/license/)**:
    - `LicenseScreen.kt` (新規): ライセンス一覧を表示する Compose 画面。
- **Navigation (ui/navigation/)**:
    - `Destination.kt` (既存または新規定義): ライセンス画面へのルートを追加。
- **Entry Point**:
    - `MainActivity.kt`: Navigation グラフに `License` 画面を登録。
    - `UserNameInputScreen.kt` (既存): 設定アイコンまたはメニューからライセンス画面へ遷移する導線を追加。

## 3. 実装詳細

- **データ構造**:
    - `LicenseItem` データクラスを定義（ライブラリ名、ライセンス名、本文等）。
    - 当面は主要ライブラリ（Compose, Hilt, Ktor, Serialization, etc.）をハードコードしたリストを表示する。
- **UI 構成**:
    - `Scaffold` を使用し、`TopAppBar` にタイトルと戻るボタンを配置。
    - `LazyColumn` を用いてライブラリ一覧を表示。
    - **詳細表示**: 各アイテムをタップした際、ライセンスの全文を表示する**ダイアログ**を表示する。
- **リソース**:
    - 文字列は `res/values/strings.xml` に定義する。

## 4. 検証・テスト観点

- **Compose Preview**:
    - `LicenseScreenPreview`: 正常なリスト表示状態を確認。
    - `LicenseItemPreview`: 単一のアイテムの表示（長いテキストの折り返し等）を確認。
    - `LicenseDetailDialogPreview`: ライセンス詳細ダイアログの表示確認。
- **Unit Test**:
    - 画面遷移用の Route 定義が正しいこと（Serialization の互換性）。
- **Instrumented Test (`androidTest/`)**:
    - `LicenseScreenTest`: 画面が正しく表示され、リスト内に特定のライブラリ名が存在することを確認。
    - アイテムタップ時にダイアログが表示されることを確認。
    - 戻るボタン押下時に遷移元に戻ることを確認。
