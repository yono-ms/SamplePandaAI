package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.UserNameInputViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class UserNameInputScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // ViewModelをMock化
    private val viewModel: UserNameInputViewModel = mockk(relaxed = true)

    @Test
    fun userNameInputScreen_showsTitleAndInput() {
        // ViewModelの状態を設定
        every { viewModel.userName } returns MutableStateFlow("")
        every { viewModel.errorMessage } returns MutableStateFlow(null)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameInputScreen(
                    onNavigateToRepoList = {},
                    onNavigateToHistory = {},
                    viewModel = viewModel
                )
            }
        }

        // UI要素が表示されているか確認
        composeTestRule.onNodeWithText("ユーザー名を入力してください").assertIsDisplayed()
        composeTestRule.onNodeWithText("GitHub ユーザー名").assertIsDisplayed()
        composeTestRule.onNodeWithText("リポジトリを取得する").assertIsDisplayed()
    }

    @Test
    fun userNameInputScreen_showsError_whenValidationFails() {
        val errorText = "GitHubのユーザー名形式が正しくありません"
        every { viewModel.userName } returns MutableStateFlow("invalid--name")
        every { viewModel.errorMessage } returns MutableStateFlow(errorText)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameInputScreen(
                    onNavigateToRepoList = {},
                    onNavigateToHistory = {},
                    viewModel = viewModel
                )
            }
        }

        // エラーメッセージが表示されているか確認
        composeTestRule.onNodeWithText(errorText).assertIsDisplayed()
    }

    @Test
    fun userNameInputScreen_callsNavigateToHistory_whenIconClicked() {
        var historyClicked = false
        every { viewModel.userName } returns MutableStateFlow("")
        every { viewModel.errorMessage } returns MutableStateFlow(null)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameInputScreen(
                    onNavigateToRepoList = {},
                    onNavigateToHistory = { historyClicked = true },
                    viewModel = viewModel
                )
            }
        }

        // 履歴アイコンをクリック
        composeTestRule.onNodeWithContentDescription("履歴を表示").performClick()

        // コールバックが呼ばれたか確認
        assert(historyClicked)
    }
}
