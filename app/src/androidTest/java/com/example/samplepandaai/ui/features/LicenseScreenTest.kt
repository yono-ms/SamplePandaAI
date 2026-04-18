package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.ui.features.license.LicenseItem
import com.example.samplepandaai.ui.features.license.LicenseScreen
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LicenseScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun licenseScreen_displaysListAndNavigates() {
        var backClicked = false
        var clickedItem: LicenseItem? = null

        composeTestRule.setContent {
            SamplePandaAITheme {
                LicenseScreen(
                    onBackClick = { backClicked = true },
                    onLicenseClick = { clickedItem = it }
                )
            }
        }

        // 1. タイトルの表示確認
        composeTestRule.onNodeWithText(context.getString(R.string.license_title))
            .assertIsDisplayed()

        // 2. リストアイテムの表示確認
        composeTestRule.onNodeWithText("Jetpack Compose").assertIsDisplayed()

        // 3. アイテムタップでコールバックが呼ばれるか (旧ダイアログ検証からの変更)
        composeTestRule.onNodeWithText("Jetpack Compose").performClick()
        assertEquals("Jetpack Compose", clickedItem?.name)
        assertEquals("https://raw.githubusercontent.com/androidx/androidx/androidx-main/LICENSE.txt", clickedItem?.url)

        // 4. 戻るボタンの動作確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back_button_content_description))
            .performClick()
        assertTrue(backClicked)
    }
}
