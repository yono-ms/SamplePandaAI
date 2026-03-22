package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListUiState
import kotlinx.datetime.Instant
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockRepos = listOf(
        GitHubRepo(
            id = 1,
            name = "ComposeSample",
            fullName = "google/ComposeSample",
            description = "A sample project for Jetpack Compose.",
            htmlUrl = "",
            stars = 1234,
            language = "Kotlin",
            updatedAt = Instant.parse("2024-01-01T00:00:00Z")
        )
    )

    @Test
    fun loadingState_showsLoadingIndicator() {
        composeTestRule.setContent {
            SamplePandaAITheme {
                RepoListContent(
                    uiState = GitHubRepoListUiState.Loading,
                    username = "google",
                    onBack = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithText("GitHub Repositories: google").assertIsDisplayed()
    }

    @Test
    fun successState_showsRepositoryList() {
        composeTestRule.setContent {
            SamplePandaAITheme {
                RepoListContent(
                    uiState = GitHubRepoListUiState.Success(mockRepos),
                    username = "google",
                    onBack = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithText("ComposeSample").assertIsDisplayed()
        composeTestRule.onNodeWithText("A sample project for Jetpack Compose.").assertIsDisplayed()
    }

    @Test
    fun errorState_showsErrorMessageAndRetryInvokesCallback() {
        var retryClicked = false
        val errorMessage = "Network Error"

        composeTestRule.setContent {
            SamplePandaAITheme {
                RepoListContent(
                    uiState = GitHubRepoListUiState.Error(errorMessage),
                    username = "google",
                    onBack = {},
                    onRetry = { retryClicked = true } // コールバックが呼ばれたらフラグを立てる
                )
            }
        }

        // エラーメッセージが表示されているか確認
        composeTestRule.onNodeWithText("Error: $errorMessage").assertIsDisplayed()

        // リトライボタンをクリック
        composeTestRule.onNodeWithText("Retry").performClick()

        // コールバックが実行された（フラグが true になった）ことを検証
        assertTrue("Retry callback should be invoked", retryClicked)
    }
}
