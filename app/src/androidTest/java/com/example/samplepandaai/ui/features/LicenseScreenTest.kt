package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.samplepandaai.ui.features.license.LicenseScreen
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import org.junit.Rule
import org.junit.Test

class LicenseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun licenseScreen_displaysListAndDialog() {
        var backClicked = false
        composeTestRule.setContent {
            SamplePandaAITheme {
                LicenseScreen(onBackClick = { backClicked = true })
            }
        }

        // 1. タイトルの表示確認
        composeTestRule.onNodeWithText("ライセンス").assertIsDisplayed()

        // 2. リストアイテムの表示確認
        composeTestRule.onNodeWithText("Jetpack Compose").assertIsDisplayed()

        // 3. アイテムタップでダイアログが表示されるか
        composeTestRule.onNodeWithText("Jetpack Compose").performClick()

        // ダイアログ固有の「閉じる」ボタンで、ダイアログが表示されたことを確認
        // Apache License 2.0 は複数あるため、ダイアログが表示された状態で一意に特定できる「閉じる」を使用
        composeTestRule.onNodeWithText("閉じる").assertIsDisplayed()

        // 4. ダイアログを閉じる
        composeTestRule.onNodeWithText("閉じる").performClick()
        composeTestRule.onNodeWithText("閉じる").assertDoesNotExist()

        // 5. 戻るボタンの動作確認
        composeTestRule.onNodeWithContentDescription("戻る").performClick()
        assert(backClicked)
    }
}
