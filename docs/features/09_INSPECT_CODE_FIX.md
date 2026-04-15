# Inspect Code 修正設計

## 1. 目的
Android Studio の "Inspect Code" 機能によって検出された警告（WARNING）や修正推奨事項に対応し、プロジェクトの品質、安全性、およびメンテナンス性を向上させる。

## 2. 検出された主要な問題の分析

`inspections/202604150721` のレポートに基づき、以下のカテゴリに分類。

### A. 依存関係と SDK の更新 (`AndroidLintGradleDependency.xml`)
- **compileSdk の更新**: 35 から 36 へのアップデート推奨。
- **ライブラリの更新**:
  - `androidx.core:core-ktx`: 1.13.1 -> 1.18.0
  - `androidx.lifecycle:lifecycle-runtime-ktx`: 2.8.4 -> 2.10.0
  - `androidx.compose:compose-bom`: 2024.08.00 -> 2026.03.01
  - `androidx.navigation:navigation-compose`: 2.8.5 -> 2.9.7
  - その他、テストライブラリ、Dagger Hilt 関連、DataStore 等。
- **KSP の更新**: 2.0.21-1.0.25 -> 2.1.0-1.0.29

### B. 不安定な API の使用 (`UnstableApiUsage.xml`)
- `app/build.gradle.kts` および `settings.gradle.kts` における `@Incubating`（実験的）API の使用。
  - `testOptions`, `repositoriesMode`, `FAIL_ON_PROJECT_REPOS` など。
  - **対応方針**: Gradle 8.x 以降で推奨される記述への移行、または必要に応じた `@OptIn` の検討。

### C. その他の Lint 警告
- `CheckTagEmptyBody.xml`: XML 内の空タグの閉じ方。
- `SpellCheckingInspection.xml`: タイポ（プロジェクト固有の用語は辞書登録）。
- `AndroidLintOldTargetApi.xml`: Target API に関する警告。

## 3. 修正計画

### フェーズ 1: ビルド基盤の更新 [DONE]
1. `gradle/libs.versions.toml` を更新。
2. `app/build.gradle.kts` の `compileSdk` を 36 に更新。
3. `gradlew clean` およびビルドが通ることを確認。

### フェーズ 2: Gradle API 警告の解消 [DONE]

1. `app/build.gradle.kts` の `exclude` 指定において、非推奨の multi-string notation (
   `group = "...", module = "..."`) を single-string notation (`"group:module"`) にリファクタリング。

### フェーズ 3: タイポの修正と辞書登録 [DONE]

1. プロジェクト固有の用語（`zulu`, `ksp`, `hilt` 等）を `.idea/dictionaries/project.xml` に登録し、誤検知を抑制。

### フェーズ 4: XML 空タグの検証 [DONE]

1. `src/main/res` および `AndroidManifest.xml` を確認し、実コード内に問題がないことを確認（一部の警告はインスペクションレポート自体に含まれる
   XML に対するものだった）。

### フェーズ 5: 最終確認とドキュメント更新 [DONE]

1. 修正結果を本ドキュメントに反映。

## 4. 修正結果と残課題

### 解決済みの事項

- `compileSdk` および `targetSdk` を 36 に引き上げ。
- Gradle の非推奨 API (multi-string notation) の排除。
- スペルチェッカーのノイズ削減。

### 保留・修正不可の事項

- **AGP 内部の警告**: `com.android.tools.lint:lint-gradle` や `com.android.tools.build:aapt2`
  の非推奨警告。これらは Android Gradle Plugin (8.9.1) の内部で発生しており、プロジェクト側での対応は不可。将来の
  AGP アップデートで解消される見込み。

## 5. 完了条件

- [x] すべての XML レポートに記載された WARNING が解消、または意図的な保留として整理されていること。
- [x] 修正後のプロジェクトが正常にビルド・テストをパスすること。
- [x] 再度 "Inspect Code" を実行し、新規の重大な警告が発生していないこと。
