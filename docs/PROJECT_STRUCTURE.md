# プロジェクト構成 (Source Tree)

このプロジェクトの主要なファイル構成です。AIエージェントはコードを生成・解析する際、必ずこの構造を参照してください。

## ディレクトリ構成

```text
SamplePandaAI/
├── app/
│   ├── build.gradle.kts           # モジュールレベルのビルド設定
│   ├── proguard-rules.pro         # 難読化設定
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── assets/
│       │   │   └── logback.xml      # Logback 設定 (Android用)
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── MainActivity.kt
│       │   │   ├── SamplePandaApplication.kt # Hilt Application クラス
│       │   │   ├── di/                 # Hilt モジュール
│       │   │   ├── data/               # Data Layer
│       │   │   │   ├── remote/         # Ktor API Client, HttpClientFactory
│       │   │   │   └── repository/     # Repository 実装
│       │   │   ├── domain/             # Domain Layer
│       │   │   │   ├── model/          # ドメインモデル
│       │   │   │   ├── repository/     # Repository インターフェース
│       │   │   │   └── usecase/        # UseCase クラス
│       │   │   ├── ui/                 # UI Layer
│       │   │   │   ├── components/     # 共通Composeコンポーネント
│       │   │   │   ├── features/       # 画面(Screen)ごとの実装
│       │   │   │   ├── theme/          # Compose Theme (Color, Type, etc.)
│       │   │   │   └── viewmodel/      # ViewModel
│       │   │   └── util/               # Utilities (シリアライザー等)
│       │   ├── openapi/
│       │   │   └── github_repos.yaml   # GitHub API定義 (OpenAPI)
│       │   └── res/                    # アプリ本番用リソース
│       ├── test/                       # ユニットテスト (JUnit4, MockK)
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── TestUtils.kt        # テスト用ユーティリティ
│       │   │   ├── data/
│       │   │   │   ├── remote/         # API通信テスト
│       │   │   │   └── repository/     # Repository/MockWebServerテスト
│       │   │   ├── domain/usecase/     # UseCase単体テスト
│       │   │   └── ui/viewmodel/       # ViewModel単体テスト
│       │   └── resources/              # テスト専用リソース
│       │       └── github_repos_success.json # テスト用成功レスポンスJSON
│       └── androidTest/                # インストゥルメントテスト (結合/UIテスト)
│           └── java/com/example/samplepandaai/
│               ├── di/                 # テスト用Hiltモジュール
│               ├── integration/        # 結合テスト
│               └── ui/features/        # UIテスト
├── docs/
│   ├── AGENTS.md              # AIエージェント活用指針
│   ├── ARCHITECTURE_DESIGN.md # 構成設計・技術的負債
│   ├── GEMINI.md              # Gemini 固有ルール
│   ├── NEXT_STEPS.md          # 次回の作業予定
│   └── PROJECT_STRUCTURE.md    # 本ファイル（プロジェクト構造の定義）
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── .gitignore
```

## パッケージ名

`com.example.samplepandaai`

## 主要な技術スタック

- **UI**: Jetpack Compose
- **DI**: Hilt
- **Networking**: Ktor
- **Testing**: MockWebServer, MockEngine, MockK, JUnit4
- **API Spec**: OpenAPI (DTO automated generation)
