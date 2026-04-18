# ライセンスリンクの正確性向上 (IMPROVE_LICENSE_LINKS)

## 1. ゴール (Goal)
- `LicenseDataProvider.kt` で提供される各ライブラリのライセンス URL を、GitHub 等のリポジトリ内にある具体的な `LICENSE` ファイル（raw content）へ更新する。
- **注意**: 今回のタスクではライブラリの増減は行わず、既存の 6 つを維持する。
- `IsSafeDomainUseCase.kt` の許可ドメインリストを更新後の URL に合わせて適切に再整理する。

## 2. 影響範囲 (Scope)
- `LicenseDataProvider.kt`: ライセンスリンク URL の更新。
- `IsSafeDomainUseCase.kt`: 許可ドメインリストの更新。
- `docs/TEST_STRATEGY.md`: ステータスの更新。

## 3. 構成 (Structure)

### 3.1. ライセンス URL の更新
現行の 6 ライブラリについて、以下の URL に更新する。
- Jetpack Compose: https://raw.githubusercontent.com/androidx/androidx/androidx-main/LICENSE.txt
- Hilt: https://raw.githubusercontent.com/google/dagger/master/LICENSE.txt
- Ktor: https://raw.githubusercontent.com/ktorio/ktor/main/LICENSE
- Kotlinx Serialization: https://raw.githubusercontent.com/Kotlin/kotlinx.serialization/master/LICENSE
- MockK: https://raw.githubusercontent.com/mockk/mockk/master/LICENSE
- SLF4J: https://raw.githubusercontent.com/qos-ch/slf4j/master/LICENSE.txt

### 3.2. 許可ドメインの更新案 (`IsSafeDomainUseCase.kt`)
- `raw.githubusercontent.com` を追加。
- 不要になったドメイン (`www.apache.org`, `opensource.org`, `ktor.io`, `kotlinlang.org`) を削除。

## 4. テスト観点 (Test Cases)

### 4.1. Unit Test (`IsSafeDomainUseCaseTest`)
- [ ] `raw.githubusercontent.com` の URL が許可されること。
- [ ] 削除されたドメインの URL が拒否されること。

### 4.2. UI Test (`LicenseScreenTest`, `LicenseDetailScreenTest`)
- [ ] 変更後の URL で `LicenseDetailScreen` (WebView) が正しく表示されること。

### 4.3. ドキュメント更新
- [ ] `TEST_STRATEGY.md` の以下の項目のステータスを `✅` に更新。
    - U-4: `IsSafeDomainUseCaseTest`
    - S-8: `SafeWebViewClientTest`
    - S-10: `LicenseScreenTest`

## 5. PR 概要
- ライセンス一覧のリンクを、リポジトリ内の直リンクに更新しました。
- 許可ドメインを `raw.githubusercontent.com` に絞り込み、セキュリティを向上させました。
- ロードマップを整理し、ライブラリ一覧の精査を別タスクとして切り出しました。
