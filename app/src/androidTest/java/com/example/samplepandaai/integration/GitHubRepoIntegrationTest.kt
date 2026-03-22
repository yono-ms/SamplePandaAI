package com.example.samplepandaai.integration

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
class GitHubRepoIntegrationTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var context: Context

    @Before
    fun init() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun app_launches_and_shows_repositories_from_mock_engine() {
        // 1. 初期画面 (UserNameInputScreen) で入力を完了させる
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_field_label))
            .performTextInput("google")
        composeTestRule.onNodeWithText(context.getString(R.string.user_name_submit_button))
            .performClick()

        // 2. 非同期でのデータ読み込み完了を待機する (最大5秒)
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodes(hasText("sample-repo"))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // 3. 読み込みが完了した後の表示を確認
        composeTestRule.onNodeWithText("sample-repo").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is a sample repository").assertIsDisplayed()
        composeTestRule.onNodeWithText("★ 99").assertIsDisplayed()
    }
}
