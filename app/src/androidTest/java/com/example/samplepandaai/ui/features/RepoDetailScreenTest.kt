package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplepandaai.domain.usecase.IsGitHubDomainUseCase
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun repoDetailScreen_displaysTitleAndHandlesBackClick() {
        var backClicked = false
        val testTitle = "Test Repository"
        val testUrl = "https://github.com/example/repo"
        val isGitHubDomainUseCase = IsGitHubDomainUseCase()

        composeTestRule.setContent {
            RepoDetailScreen(
                url = testUrl,
                title = testTitle,
                onBackClick = { backClicked = true },
                isGitHubDomainUseCase = isGitHubDomainUseCase
            )
        }

        // 1. タイトルが表示されているか確認
        composeTestRule.onNodeWithText(testTitle).assertIsDisplayed()

        // 2. 戻るボタンが機能するか確認
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assertTrue("Back click should trigger onBackClick callback", backClicked)
    }
}
