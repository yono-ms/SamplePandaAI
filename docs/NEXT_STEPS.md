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
  - [ ] Run all tests (Unit & Instrumented). (F5)

## TODO (Next Actions)

1. **Test Execution**: 関連するすべてのテストを実行し、正常動作を確認する。
2. **Review & Merge**: テスト結果を報告し、承認を得る。

## Technical Memo

- Lite Flow を適用（Phase 4 スキップ）。
- `IsSafeDomainUseCaseTest` はパス済み。
- 次に `LicenseScreenTest` 等の UI テストを含む全テストのパスを確認する。
