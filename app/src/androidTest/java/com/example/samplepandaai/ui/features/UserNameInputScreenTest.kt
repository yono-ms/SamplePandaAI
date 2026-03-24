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
import com.example.samplepandaai.ui.viewmodel.UserNameInputViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserNameInputScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var context: Context
    private val viewModel: UserNameInputViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun userNameInputScreen_showsTitleAndInput() {
        every { viewModel.userName } returns MutableStateFlow("")
        every { viewModel.errorMessage } returns MutableStateFlow(null)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameInputScreen(
                    onNavigateToRepoList = {},
                    onNavigateToHistory = {},
                    onNavigateToLicense = {},
                    viewModel = viewModel
                )
            }
        }

        // リソースから文字列を取得して確認
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_input_instruction))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_field_label))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_submit_button))
            .assertIsDisplayed()
    }

    @Test
    fun userNameInputScreen_showsError_whenValidationFails() {
        // ViewModelから返されるエラーメッセージ（通常はUseCaseから渡される）
        val errorText = context.getString(R.string.error_invalid_user_name)
        every { viewModel.userName } returns MutableStateFlow("invalid--name")
        every { viewModel.errorMessage } returns MutableStateFlow(errorText)

        composeTestRule.setContent {
            SamplePandaAITheme {
                UserNameInputScreen(
                    onNavigateToRepoList = {},
                    onNavigateToHistory = {},
                    onNavigateToLicense = {},
                    viewModel = viewModel
                )
            }
        }

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
                    onNavigateToLicense = {},
                    viewModel = viewModel
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.user_name_history_icon_content_description))
            .performClick()

        assert(historyClicked)
    }
}
