# 構成設計 (Architecture Design)

このドキュメントでは、GitHub APIを使用してリポジトリリストを表示する機能のアーキテクチャ設計について記述します。
特に「試験フェーズ」を重視し、各レイヤーでの単体テストおよびKtorを用いた通信周りの柔軟なテスト構成を検証します。

## アーキテクチャ階層

Clean Architectureの考え方に基づき、以下の3レイヤー構成を採用します。

### 1. Presentation Layer (UI & ViewModel)

- **技術**: Jetpack Compose, ViewModel
- **責務**: UIの構築、状態管理、ユーザーインタラクションの処理。
- **テスト**:
    - **ViewModel単体テスト**: UseCaseをモックし、UI状態（State）の遷移を検証。
    - **Compose UIテスト**: UIコンポーネントの表示・挙動を検証。

### 2. Domain Layer (Business Logic)

- **技術**: UseCase (Pure Kotlin)
- **責務**: アプリケーション固有のビジネスロジック。
- **テスト**:
    - **UseCase単体テスト**: Repositoryをモックし、ビジネスロジックの正当性を検証。

### 3. Data Layer (Repository & Remote/Local Source)

- **技術**: Ktor, Kotlin Serialization
- **責務**: データの取得（GitHub API）とドメインモデルへの変換。
- **テスト**:
    - **Repository単体テスト**: Ktorの `MockEngine` を使用し、ネットワーク層のレスポンス処理を検証。
    - **対抗試験**: 実サーバーまたはモックサーバーへの接続試験。

## 共通基盤 (Infrastructure / Cross-Cutting Concerns)

### ログ基盤 (Logging)

- **技術**: SLF4J (Simple Logging Facade for Java)
- **実装**: `logback-android` (Android環境), `slf4j-simple` (JUnitテスト環境)
- **選定理由**:
    - 抽象化レイヤーとして機能し、テスト時にログの実装を容易に差し替えられる。
    - JVM上での純粋な単体テスト（Domain層など）でも、Androidの `Log` クラスに依存せずログ出力が可能。
    - Ktor 内部でのログ出力との親和性が高い。

## 通信周りの設計 (Ktor Engine Strategy)

Ktorの `HttpClient` 構成において、 `HttpClientEngine` を差し替え可能にすることで、様々な試験シナリオに対応します。

| シナリオ            | エンジン               | 目的                               |
|:----------------|:-------------------|:---------------------------------|
| **本番 / デバッグ実行** | `OkHttp` または `CIO` | 実際のネットワーク通信。                     |
| **ユニットテスト**     | `MockEngine`       | サーバーを介さず、定義したJSONレスポンスを返す。       |
| **外部モックサーバー対抗** | `OkHttp`           | 実際のHTTPプロトコルを用いた外部モックサーバーとの通信検証。 |

## 予定されるパッケージ構成

```text
com.example.samplepandaai/
├── data/
│   ├── remote/          # Ktor API Client, DTO
│   └── repository/      # Repository 実装
├── domain/
│   ├── model/           # ドメインモデル
│   ├── repository/      # Repository インターフェース
│   └── usecase/         # UseCase クラス
├── ui/
│   ├── features/        # 機能ごとのCompose UI
│   └── viewmodel/       # ViewModel
└── util/
    └── logging/         # ログ関連のユーティリティ（必要な場合）
```
