# プロジェクト構成 (Source Tree)

このプロジェクトの主要なファイル構成です。AIエージェントはコードを生成・解析する際、必ずこの構造を参照してください。

## ディレクトリ構成

```text
SamplePandaAI/
├── app/
│   ├── build.gradle.kts           # モジュールレベルのビルド設定 (packaging 設定追加)
│   ├── proguard-rules.pro         # 難読化設定
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── assets/
│       │   │   └── logback.xml      # Logback 設定 (Android用)
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── MainActivity.kt   # 画面遷移 (NavHost) の定義
│       │   │   ├── SamplePandaApplication.kt # Hilt Application クラス
│       │   │   ├── di/                 # Hilt モジュール
│       │   │   ├── data/               # Data Layer
│       │   │   │   ├── remote/         # Ktor API Client, HttpClientFactory
│       │   │   │   └── repository/     # Repository 実装 (UserNameRepositoryImpl 等)
│       │   │   ├── domain/             # Domain Layer
│       │   │   │   ├── model/          # ドメインモデル
│       │   │   │   ├── repository/     # Repository インターフェース
│       │   │   │   └── usecase/        # UseCase クラス (ValidateGitHubUserNameUseCase 等)
│       │   │   ├── ui/                 # UI Layer
│       │   │   │   ├── components/     # 共通Composeコンポーネント
│       │   │   │   ├── features/       # 各画面の実装 (UserNameInput, UserNameHistory, RepoList)
│       │   │   │   ├── navigation/     # Type-safe Navigation の定義 (RepoList, UserNameInput 等)
│       │   │   │   ├── theme/          # Compose Theme (Color, Type, etc.)
│       │   │   │   └── viewmodel/      # ViewModel (各画面に対応)
│       │   │   └── util/               # Utilities (シリアライザー等)
│       │   ├── openapi/
│       │   │   └── github_repos.yaml   # GitHub API定義 (OpenAPI)
│       │   └── res/                    # アプリ本番用リソース
│       │       └── values/
│       │           └── strings.xml     # アプリ内の全文字列リソース
│       ├── test/                       # ユニットテスト (JUnit4, MockK)
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── data/
│       │   │   ├── domain/usecase/     # UseCase 単体テスト
│       │   │   └── ui/viewmodel/       # ViewModel 単体テスト
│       └── androidTest/                # インストゥルメントテスト (結合/UIテスト)
│           └── java/com/example/samplepandaai/
│               ├── integration/        # 画面間遷移の結合テスト (UserNameIntegrationTest 等)
│               └── ui/features/        # UI 単体テスト (各 ScreenTest)
├── docs/
│   ├── features/              # 機能ごとの詳細設計・ドキュメント
│   │   └── 01_USER_NAME_INPUT.md
│   ├── AGENTS.md              # AIエージェント活用指針
│   ├── ARCHITECTURE_DESIGN.md # 構成設計・技術的負債
│   ├── DESIGN_COST_VERIFICATION.md # 設計・コスト検証ドキュメント
│   ├── GEMINI.md              # Gemini 固有ルール
│   ├── NEXT_STEPS.md          # 次回の作業予定
│   ├── PROJECT_STRUCTURE.md    # 本ファイル (最新のプロジェクト構造)
│   └── TEST_STRATEGY.md        # テスト戦略ドキュメント
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── .gitignore
```

## パッケージ名

`com.example.samplepandaai`

## 主要な技術スタック

- **UI**: Jetpack Compose (Material Design 3)
- **DI**: Hilt
- **Navigation**: Type-safe Navigation (Kotlin Serialization)
- **Data Persistence**: Preferences DataStore
- **Networking**: Ktor
- **Testing**: JUnit4, MockK, Compose Test
- **API Spec**: OpenAPI (DTO automated generation)
