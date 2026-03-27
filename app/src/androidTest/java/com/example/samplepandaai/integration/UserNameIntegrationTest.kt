package com.example.samplepandaai.integration

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import com.example.samplepandaai.MainActivity
import com.example.samplepandaai.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class UserNameIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context

    @Before
    fun init() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testUserNameFlow_happyPath() {
        val testUser = "panda-user"

        // 1. 入力画面の確認
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_input_instruction))
            .assertIsDisplayed()

        // 2. 正常入力と遷移
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_field_label))
            .performTextInput(testUser)

        composeTestRule.onNodeWithText(context.getString(R.string.user_name_submit_button))
            .performClick()

        // 3. 一覧画面への到達確認 (遷移待ちを考慮)
        val expectedTitle = context.getString(R.string.repo_list_title, testUser)
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule.onAllNodesWithText(expectedTitle)
                .fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithText(expectedTitle).assertIsDisplayed()

        // 4. 戻って履歴を確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back_button_content_description))
            .performClick()

        // 入力画面に戻ったことを確認
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.user_name_history_icon_content_description))
            .performClick()

        composeTestRule.onNodeWithText(testUser).assertIsDisplayed()
    }

    @Test
    fun testUserNameFlow_licenseNavigation() {
        // 新しく追加したライセンス画面への遷移テスト
        composeTestRule.onNodeWithContentDescription(context.getString(R.string.license_menu_item))
            .performClick()

        composeTestRule.onNodeWithText(context.getString(R.string.license_title))
            .assertIsDisplayed()

        composeTestRule.onNodeWithContentDescription(context.getString(R.string.back_button_content_description))
            .performClick()

        composeTestRule.onNodeWithText(context.getString(R.string.user_name_input_instruction))
            .assertIsDisplayed()
    }

    @Test
    fun testUserNameFlow_validationError_doesNotNavigate() {
        val invalidUser = "-invalid-"

        // 1. 不正な入力
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_field_label))
            .performTextInput(invalidUser)
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_submit_button))
            .performClick()

        // 2. エラーメッセージの表示確認
        composeTestRule.onNodeWithText(context.getString(R.string.error_invalid_user_name))
            .assertIsDisplayed()

        // 3. 画面遷移が起きていない
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_input_instruction))
            .assertIsDisplayed()
    }

    @Test
    fun testUserNameFlow_emptyInput_showsError() {
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_submit_button))
            .performClick()
        composeTestRule.onNodeWithText(context.getString(R.string.error_empty_user_name))
            .assertIsDisplayed()
    }
}
