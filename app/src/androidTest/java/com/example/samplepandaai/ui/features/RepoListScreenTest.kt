package com.example.samplepandaai.ui.features

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListUiState
import kotlinx.datetime.Instant
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context

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

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun loadingState_showsLoadingIndicator() {
        val username = "google"
        composeTestRule.setContent {
            SamplePandaAITheme {
                RepoListContent(
                    uiState = GitHubRepoListUiState.Loading,
                    username = username,
                    onBack = {},
                    onRepoClick = {},
                    onRetry = {}
                )
            }
        }
        // フォーマット済み文字列の確認
        val expectedTitle = context.getString(R.string.repo_list_title, username)
        composeTestRule.onNodeWithText(expectedTitle).assertIsDisplayed()
    }

    @Test
    fun successState_showsRepositoryList() {
        composeTestRule.setContent {
            SamplePandaAITheme {
                RepoListContent(
                    uiState = GitHubRepoListUiState.Success(mockRepos),
                    username = "google",
                    onBack = {},
                    onRepoClick = {},
                    onRetry = {}
                )
            }
        }
        composeTestRule.onNodeWithText("ComposeSample").assertIsDisplayed()
        composeTestRule.onNodeWithText("A sample project for Jetpack Compose.").assertIsDisplayed()

        // スター数のフォーマット確認 (plurals 対応)
        val expectedStars = context.resources.getQuantityString(R.plurals.star_count, 1234, 1234)
        composeTestRule.onNodeWithText(expectedStars).assertIsDisplayed()
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
                    onRepoClick = {},
                    onRetry = { retryClicked = true }
                )
            }
        }

        // フォーマット済みエラーメッセージが表示されているか確認
        val expectedError = context.getString(R.string.error_prefix, errorMessage)
        composeTestRule.onNodeWithText(expectedError).assertIsDisplayed()

        // リトライボタンをクリック
        composeTestRule.onNodeWithText(context.getString(R.string.retry_button)).performClick()

        // コールバックが実行されたことを検証
        assertTrue("Retry callback should be invoked", retryClicked)
    }
}
