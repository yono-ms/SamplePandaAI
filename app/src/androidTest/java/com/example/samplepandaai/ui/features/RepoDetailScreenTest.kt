package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.usecase.IsSafeDomainUseCase
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun repoDetailScreen_displaysTitleAndHandlesBackClick() {
        var backClicked = false
        val testTitle = "Test Repository"
        val testUrl = "https://github.com/example/repo"
        val isSafeDomainUseCase = IsSafeDomainUseCase()

        composeTestRule.setContent {
            RepoDetailScreen(
                url = testUrl,
                title = testTitle,
                onBackClick = { backClicked = true },
                isSafeDomainUseCase = isSafeDomainUseCase
            )
        }

        // 1. タイトルが表示されているか確認
        composeTestRule.onNodeWithText(testTitle).assertIsDisplayed()

        // 2. 戻るボタンが機能するか確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back_button_content_description))
            .performClick()
        assertTrue("Back click should trigger onBackClick callback", backClicked)
    }
}
