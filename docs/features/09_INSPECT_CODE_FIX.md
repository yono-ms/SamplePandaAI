# Inspect Code 修正設計

## 1. 目的

Android Studio の "Inspect Code"
機能によって検出された警告（WARNING）や修正推奨事項に対応し、プロジェクトの品質、安全性、およびメンテナンス性を向上させる。あわせて、最新のビルド環境（AGP
9.1.1 / SDK 37）への適合を行う。

## 2. 検出された主要な問題の分析

`inspections/202604150721` のレポートおよびビルド過程で判明した課題。

### A. ビルド基盤の更新

- **SDK の更新**: `compileSdk` および `targetSdk` を 37 にアップデート。
- **Gradle/AGP の更新**:
  - AGP: 9.1.1
  - Gradle: 9.4.1 (チェックサム検証済み)
- **Kotlin の更新**: 2.3.20 (Built-in Kotlin サポートの有効化)

### B. API の非推奨化とパッケージ移動

- **hiltViewModel**: `androidx.hilt.navigation.compose` から
  `androidx.hilt.lifecycle.viewmodel.compose` へ移動。
- **Instant 型**: `kotlinx.datetime.Instant` が非推奨となり、Kotlin 2.x 以降の標準である
  `kotlin.time.Instant` への移行が必要。

### C. Gradle DSL 警告

- `Suspicious receiver type`: `android` ブロック内の `kotlin` ブロックをトップレベルに移動。
- `gradle.properties`: 非推奨フラグ（`builtInKotlin`, `enableAppCompileTimeRClass` 等）の整理。

## 3. 修正結果

### フェーズ 1: ビルド基盤の更新 [DONE]

1. `gradle/libs.versions.toml` を更新し、AGP 9.1.1 / Kotlin 2.3.20 / SDK 37 を適用。
2. Gradle Wrapper を 9.4.1 に更新し、SHA-256 チェックサムの不一致を修正。

### フェーズ 2: Gradle API / DSL 警告の解消 [DONE]

1. `app/build.gradle.kts` の `kotlin` 設定ブロックをトップレベルへ移動。
2. `gradle.properties` の不要な互換フラグを削除。

### フェーズ 3: コードの現代化 (Kotlin 2.x 対応) [DONE]

1. `GitHubRepo.kt` 等のドメインモデルにおいて、`Instant` 型を `kotlin.time.Instant` へ移行。
2. 各画面（`MainActivity`, `UserNameInputScreen` 等）の `hiltViewModel` インポートパスを修正。

### フェーズ 4: タイポの修正と辞書登録 [DONE]

1. プロジェクト固有の用語（`zulu`, `ksp`, `hilt` 等）を `.idea/dictionaries/project.xml` に登録。

### フェーズ 5: 最終確認 [DONE]

1. 全 52 件のユニットテストおよび 19 件の Android Test がパスすることを確認。

## 4. 残課題

- **AGP 内部の警告**: `lint-gradle` や `aapt2` の非推奨警告は AGP 内部に起因するため、将来の AGP
  アップデートを待機。

## 5. 完了条件

- [x] すべての主要な WARNING が解消されていること。
- [x] 修正後のプロジェクトが正常にビルド・テストをパスすること。
- [x] ドキュメントが最終実装と一致していること。
