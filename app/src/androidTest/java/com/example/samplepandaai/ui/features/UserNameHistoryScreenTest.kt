package com.example.samplepandaai.ui.features

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.R
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.UserNameHistoryViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserNameHistoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private val viewModel: UserNameHistoryViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun userNameHistoryScreen_showsHistoryList() {
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

        // 履歴アイテムが表示されているか確認
        composeTestRule.onNodeWithText("user1").assertIsDisplayed()
        composeTestRule.onNodeWithText("user2").assertIsDisplayed()
    }

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

        // 空の状態のメッセージを確認
        composeTestRule.onNodeWithText(context.getString(R.string.history_empty))
            .assertIsDisplayed()
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

        // 削除ボタンをクリック（ContentDescriptionで検索）
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.history_delete_content_description))
            .performClick()

        // ViewModelの削除メソッドが呼ばれたか確認
        verify { viewModel.onDeleteUserName("user1") }
    }
}
