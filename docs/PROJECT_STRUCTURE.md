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
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── di/                     # Hilt モジュール
│       │   │   ├── data/                   # Data Layer (Ktor, Repository)
│       │   │   ├── domain/                 # Domain Layer (Model, UseCase)
│       │   │   ├── ui/                     # UI Layer (Compose, ViewModel)
│       │   │   └── util/                   # Utilities
│       │   ├── openapi/
│       │   │   └── github_repos.yaml    # GitHub API定義
│       │   └── res/                     # アプリ本番用リソース
│       ├── test/                        # ユニットテスト (本番ビルドには含まれない)
│       │   ├── java/com/example/samplepandaai/
│       │   │   ├── TestUtils.kt         # テスト用ユーティリティ
│       │   │   ├── data/
│       │   │   │   └── repository/
│       │   │   │       └── GitHubRepositoryMockWebServerTest.kt # MockWebServerテスト
│       │   │   ├── data/remote/
│       │   │   │   └── GitHubApiServiceTest.kt              # API通信テスト
│       │   │   ├── domain/usecase/
│       │   │   │   └── GetGitHubReposUseCaseTest.kt         # UseCase単体テスト
│       │   │   └── ui/viewmodel/
│       │   │       └── GitHubRepoListViewModelTest.kt       # ViewModel単体テスト
│       │   └── resources/               # テスト専用リソース
│       │       └── github_repos_success.json # テスト用成功レスポンスJSON
│       └── androidTest/                 # インストゥルメントテスト
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
