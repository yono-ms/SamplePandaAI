# プロジェクト構成 (Source Tree)

このプロジェクトの主要なファイル構成です。AIエージェントはコードを生成・解析する際、必ずこの構造を参照してください。

## ディレクトリ構成

```text
SamplePandaAI/
├── .github/
│   └── workflows/
│       └── ci.yml             # GitHub Actions ワークフロー定義 (Build/Test 自動化)
├── app/
│   ├── build.gradle.kts           # モジュールレベルのビルド設定
│   ├── proguard-rules.pro         # 難読化設定
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── MainActivity.kt
│       │   │   ├── di/
│       │   │   ├── data/
│       │   │   ├── domain/
│       │   │   │   └── usecase/
│       │   │   │       ├── IsGitHubDomainUseCase.kt   # NEW: URLドメイン判定
│       │   │   ├── ui/
│       │   │   │   ├── features/
│       │   │   │   │   ├── RepoDetailScreen.kt        # NEW: リポジトリ詳細画面
│       │   │   │   │   └── GitHubWebViewClient.kt     # NEW: WebView制御
│       │   │   └── util/
│       │   ├── openapi/
│       │   └── res/
│       ├── test/                       # ユニットテスト
│       └── androidTest/                # インストゥルメントテスト
├── docs/
│   ├── features/
│   │   ├── 01_USER_NAME_INPUT.md
│   │   ├── 02_LICENSE_SCREEN.md
│   │   ├── 03_I18N.md
│   │   ├── 04_CI_SETUP.md     # CI/CD 設定詳細 (GitHub Actions)
│   │   └── 05_REPO_DETAIL.md  # NEW: リポジトリ詳細画面の実装詳細
│   ├── AGENTS.md
│   ├── ARCHITECTURE_DESIGN.md
│   ├── DESIGN_COST_VERIFICATION.md
│   ├── GEMINI.md
│   ├── NEXT_STEPS.md
│   ├── PROJECT_STRUCTURE.md
│   └── TEST_STRATEGY.md
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
└── .gitignore
```

## パッケージ名

`com.example.samplepandaai`

## 主要な技術スタック

- **CI/CD**: GitHub Actions
- **UI**: Jetpack Compose (Material Design 3)
- **DI**: Hilt
- **Navigation**: Type-safe Navigation (Kotlin Serialization)
- **Data Persistence**: Preferences DataStore
- **Networking**: Ktor
- **Testing**: JUnit4, MockK, Compose Test
- **API Spec**: OpenAPI (DTO automated generation)
- **i18n**: Android String Resources (ja, en, zh, de, ar)
