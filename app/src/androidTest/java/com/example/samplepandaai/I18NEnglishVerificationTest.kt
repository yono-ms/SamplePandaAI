package com.example.samplepandaai

import android.content.res.Configuration
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Locale

/**
 * 設計書 03_I18N.md に基づく、英語ロケールのリテラル文字列正当性検証テスト。
 * UI 描画を介さず、英語ロケールの Context から直接文字列を取得して検証することで、
 * 副作用を排除しつつ翻訳カタログの正当性を担保する。
 */
@HiltAndroidTest
class I18NEnglishVerificationTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun verifyEnglishCatalog_isCorrect() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // 英語ロケールの Context を動的に作成 (副作用なし)
        val config =
            Configuration(context.resources.configuration).apply { setLocale(Locale.ENGLISH) }
        val localizedContext = context.createConfigurationContext(config)

        // 英語のリテラル文字列でアサーション (翻訳カタログの正当性検証)
        assertEquals(
            "Please enter a GitHub username",
            localizedContext.getString(R.string.user_name_input_instruction)
        )
        // strings.xml (en) の定義に合わせて "GitHub Username" (大文字) に修正
        assertEquals(
            "GitHub Username",
            localizedContext.getString(R.string.user_name_field_label)
        )
        assertEquals(
            "Fetch Repositories",
            localizedContext.getString(R.string.user_name_submit_button)
        )
        assertEquals(
            "License Information",
            localizedContext.getString(R.string.license_menu_item)
        )
    }
}
