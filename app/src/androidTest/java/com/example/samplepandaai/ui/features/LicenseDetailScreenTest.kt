package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.usecase.IsSafeDomainUseCase
import com.example.samplepandaai.ui.features.license.LicenseDetailScreen
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class LicenseDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun licenseDetailScreen_displaysTitleAndBackButton() {
        var backClicked = false
        val testUrl = "https://www.apache.org/licenses/LICENSE-2.0"
        val testTitle = "Apache License 2.0"

        composeTestRule.setContent {
            SamplePandaAITheme {
                LicenseDetailScreen(
                    url = testUrl,
                    title = testTitle,
                    onBackClick = { backClicked = true },
                    isSafeDomainUseCase = IsSafeDomainUseCase()
                )
            }
        }

        // 1. タイトルの表示確認
        composeTestRule.onNodeWithText(testTitle).assertIsDisplayed()

        // 2. 戻るボタンの表示と動作確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back))
            .assertIsDisplayed()
            .performClick()

        assertTrue(backClicked)
    }
}
