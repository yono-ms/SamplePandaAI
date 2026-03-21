package com.example.samplepandaai.integration

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.example.samplepandaai.MainActivity
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

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testUserNameFlow_happyPath() {
        val testUser = "panda-user"

        // 1. 入力画面の確認
        composeTestRule.onNodeWithText("ユーザー名を入力してください").assertIsDisplayed()

        // 2. 正常入力と遷移
        composeTestRule.onNodeWithText("GitHub ユーザー名").performTextInput(testUser)
        composeTestRule.onNodeWithText("リポジトリを取得する").performClick()

        // 3. 一覧画面への到達確認
        composeTestRule.onNodeWithText("GitHub Repositories: $testUser").assertIsDisplayed()

        // 4. 戻って履歴を確認
        composeTestRule.onNodeWithContentDescription("Back").performClick()
        composeTestRule.onNodeWithContentDescription("履歴を表示").performClick()
        composeTestRule.onNodeWithText(testUser).assertIsDisplayed()
    }

    @Test
    fun testUserNameFlow_validationError_doesNotNavigate() {
        val invalidUser = "-invalid-"

        // 1. 不正な入力
        composeTestRule.onNodeWithText("GitHub ユーザー名").performTextInput(invalidUser)
        composeTestRule.onNodeWithText("リポジトリを取得する").performClick()

        // 2. エラーメッセージの表示確認
        composeTestRule.onNodeWithText("GitHubのユーザー名形式が正しくありません")
            .assertIsDisplayed()

        // 3. 画面遷移が起きていない（まだ入力画面にいる）ことの確認
        composeTestRule.onNodeWithText("ユーザー名を入力してください").assertIsDisplayed()
    }

    @Test
    fun testUserNameFlow_emptyInput_showsError() {
        // 1. 未入力でクリック
        composeTestRule.onNodeWithText("リポジトリを取得する").performClick()

        // 2. エラーメッセージの表示確認
        composeTestRule.onNodeWithText("ユーザー名を入力してください").assertIsDisplayed()
    }
}
