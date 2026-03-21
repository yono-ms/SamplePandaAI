package com.example.samplepandaai.ui.features

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.UserNameHistoryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class UserNameHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: UserNameHistoryViewModel = mockk(relaxed = true)

    @Test
    fun userNameHistoryScreen_showsEmptyMessage_whenHistoryIsEmpty() {
        every { viewModel.history } returns MutableStateFlow(emptyList())

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameHistoryScreen(
                    onNavigateToRepoList = {},
                    onBack = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("履歴はありません").assertIsDisplayed()
    }

    @Test
    fun userNameHistoryScreen_showsHistoryItems() {
        val history = listOf("user1", "user2")
        every { viewModel.history } returns MutableStateFlow(history)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameHistoryScreen(
                    onNavigateToRepoList = {},
                    onBack = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithText("user1").assertIsDisplayed()
        composeTestRule.onNodeWithText("user2").assertIsDisplayed()
    }

    @Test
    fun userNameHistoryScreen_callsDelete_whenDeleteButtonClicked() {
        val history = listOf("user1")
        every { viewModel.history } returns MutableStateFlow(history)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameHistoryScreen(
                    onNavigateToRepoList = {},
                    onBack = {},
                    viewModel = viewModel
                )
            }
        }

        // 削除ボタンをクリック
        composeTestRule.onNodeWithContentDescription("削除").performClick()

        // ViewModelの削除メソッドが呼ばれたか確認
        verify { viewModel.onDeleteUserName("user1") }
    }

    @Test
    fun userNameHistoryScreen_callsNavigateToRepoList_whenItemClicked() {
        var navigatedUser: String? = null
        val history = listOf("user1")
        every { viewModel.history } returns MutableStateFlow(history)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameHistoryScreen(
                    onNavigateToRepoList = { navigatedUser = it },
                    onBack = {},
                    viewModel = viewModel
                )
            }
        }

        // 履歴項目をクリック
        composeTestRule.onNodeWithText("user1").performClick()

        // コールバックが呼ばれたか確認
        assert(navigatedUser == "user1")
    }
}
