package com.example.samplepandaai.ui.features

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.ui.features.license.LicenseScreen
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LicenseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun licenseScreen_displaysListAndDialog() {
        var backClicked = false
        composeTestRule.setContent {
            SamplePandaAITheme {
                LicenseScreen(onBackClick = { backClicked = true })
            }
        }

        // 1. タイトルの表示確認
        composeTestRule.onNodeWithText(context.getString(R.string.license_title))
            .assertIsDisplayed()

        // 2. リストアイテムの表示確認 (データは固定なのでそのまま)
        composeTestRule.onNodeWithText("Jetpack Compose").assertIsDisplayed()

        // 3. アイテムタップでダイアログが表示されるか
        composeTestRule.onNodeWithText("Jetpack Compose").performClick()

        // ダイアログ固有の「閉じる」ボタンで、ダイアログが表示されたことを確認
        composeTestRule.onNodeWithText(context.getString(R.string.license_dialog_close))
            .assertIsDisplayed()

        // 4. ダイアログを閉じる
        composeTestRule.onNodeWithText(context.getString(R.string.license_dialog_close))
            .performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.license_dialog_close))
            .assertDoesNotExist()

        // 5. 戻るボタンの動作確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back_button_content_description))
            .performClick()
        assert(backClicked)
    }
}
