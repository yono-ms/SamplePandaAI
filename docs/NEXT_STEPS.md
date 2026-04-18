# Current Status

- **Task**: IMPROVE_LICENSE_LINKS (ライセンスリンクの正確性向上)
- **Branch**: feature/improve-license-links
- **Phase**: Phase 5: Test Execution (テスト実施)
- **Status**:
  - [x] Create branch `feature/improve-license-links`. (F1)
  - [x] Analyze `LicenseDataProvider.kt` and `IsSafeDomainUseCase.kt`. (F2)
  - [x] Create design doc `docs/features/10_IMPROVE_LICENSE_LINKS.md`. (F2)
  - [x] Update `ROADMAP.md` with "license list review" task. (F2)
  - [x] Update URLs in `LicenseDataProvider.kt`. (F3)
  - [x] Re-organize allowed domains in `IsSafeDomainUseCase.kt`. (F3)
  - [x] Update `IsSafeDomainUseCaseTest.kt`. (F3)
  - [x] Run all tests (Unit & Instrumented). (F5)
- [x] Resolve code analysis warnings. (F5)
- [x] Update documentation (`TEST_STRATEGY.md`, `10_IMPROVE_LICENSE_LINKS.md`). (F5)

## TODO (Next Actions)

1. **Review & Merge**: テスト結果と更新されたドキュメントを確認し、マージする。

## Technical Memo

- Lite Flow を適用（Phase 4 スキップ）。
- `IsSafeDomainUseCaseTest` はパス済み。
- 次に `LicenseScreenTest` 等の UI テストを含む全テストのパスを確認する。
