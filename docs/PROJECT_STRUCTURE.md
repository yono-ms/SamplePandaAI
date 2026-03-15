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
│       │   │   ├── data/
│       │   │   │   ├── remote/
│       │   │   │   │   ├── dto/                # OpenAPI 生成された DTO (ビルド後に生成)
│       │   │   │   │   ├── GitHubApiService.kt  # API通信サービス
│       │   │   │   │   └── HttpClientFactory.kt # Ktorクライアント設定
│       │   │   │   └── repository/
│       │   │   │       └── GitHubRepositoryImpl.kt # Repository 実装
│       │   │   ├── domain/
│       │   │   │   ├── model/
│       │   │   │   │   └── GitHubRepo.kt        # ドメインモデル
│       │   │   │   └── repository/
│       │   │   │       └── GitHubRepository.kt  # Repository インターフェース
│       │   │   ├── ui/theme/            # テーマ定義
│       │   │   │   ├── Color.kt
│       │   │   │   ├── Theme.kt
│       │   │   │   └── Type.kt
│       │   │   ├── util/
│       │   │   │   └── serialization/
│       │   │   │       ├── OffsetDateTimeKSerializer.kt # 日付用シリアライザー
│       │   │   │       └── URIKSerializer.kt            # URI用シリアライザー
│       │   │   └── MainActivity.kt      # メイン画面 (Jetpack Compose)
│       │   ├── openapi/
│       │   │   └── github_repos.yaml    # GitHub API定義
│       │   └── res/                     # リソース
│       ├── test/                        # ユニットテスト
│       │   └── java/com/example/samplepandaai/
│       │       ├── data/
│       │       │   └── repository/
│       │       │       └── GitHubRepositoryImplTest.kt # リポジトリのテスト
│       │       ├── data/remote/
│       │       │   └── GitHubApiServiceTest.kt         # API通信テスト
│       │       └── LoggingTest.kt                      # ログ出力テスト
│       └── androidTest/                 # インストゥルメントテスト
├── docs/
│   ├── AGENTS.md              # AIエージェント活用指針
│   ├── ARCHITECTURE_DESIGN.md # 構成設計・技術的負債
│   ├── GEMINI.md              # Gemini 固有ルール
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
- **Language**: Kotlin
- **Build System**: Gradle Kotlin DSL (kts)
- **Networking**: Ktor
- **Serialization**: Kotlinx Serialization
- **API Spec**: OpenAPI (DTO automated generation)
