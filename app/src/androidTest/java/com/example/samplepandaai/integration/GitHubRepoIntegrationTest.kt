package com.example.samplepandaai.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.example.samplepandaai.MainActivity
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

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun app_launches_and_shows_repositories_from_mock_engine() {
        // 非同期でのデータ読み込み完了を待機する (最大5秒)
        composeTestRule.waitUntil(timeoutMillis = 5000) {
            composeTestRule
                .onAllNodes(hasText("sample-repo"))
                .fetchSemanticsNodes().isNotEmpty()
        }

        // 読み込みが完了した後の表示を確認
        composeTestRule.onNodeWithText("sample-repo").assertIsDisplayed()

        // Repository の description も正しく表示されているか確認
        composeTestRule.onNodeWithText("This is a sample repository").assertIsDisplayed()

        // UseCase によって適用されるスター数なども表示されているか確認
        composeTestRule.onNodeWithText("★ 99").assertIsDisplayed()
    }
}
