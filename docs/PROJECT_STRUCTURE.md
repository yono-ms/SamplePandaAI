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
│       │   │   ├── MainActivity.kt      # メイン画面 (Jetpack Compose)
│       │   │   └── ui/theme/            # テーマ定義
│       │   │       ├── Color.kt
│       │   │       ├── Theme.kt
│       │   │       └── Type.kt
│       │   └── res/                     # リソース (drawable, mipmap, values, xml)
│       ├── test/                        # ユニットテスト (ExampleUnitTest.kt)
│       └── androidTest/                 # インストゥルメントテスト (ExampleInstrumentedTest.kt)
├── docs/
│   ├── AGENTS.md              # AIエージェント活用指針
│   ├── GEMINI.md              # Gemini 固有ルール
│   └── PROJECT_STRUCTURE.md    # 本ファイル（プロジェクト構造の定義）
├── gradle/                    # Gradleラッパー関連
├── build.gradle.kts           # プロジェクト全体のビルド設定
├── settings.gradle.kts        # モジュール管理
├── gradle.properties          # Gradleのプロパティ設定
├── gradlew                    # Unix用Gradleラッパースクリプト
├── gradlew.bat                # Windows用Gradleラッパースクリプト
├── .gitignore                 # Git除外設定
└── README.md                  # プロジェクト概要
```

## パッケージ名

`com.example.samplepandaai`

## 主要な技術スタック

- **UI**: Jetpack Compose
- **Language**: Kotlin
- **Build System**: Gradle Kotlin DSL (kts)
