package com.example.samplepandaai.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateGitHubUserNameUseCaseTest {

    private val useCase = ValidateGitHubUserNameUseCase()

    @Test
    fun `invoke - 有効な名前のとき、Successを返すこと`() {
        val validNames = listOf("google", "android-123", "PandaAI", "a-b-c")
        validNames.forEach { name ->
            val result = useCase(name)
            assertTrue(
                "Should be Success for $name",
                result is ValidateGitHubUserNameUseCase.Result.Success
            )
        }
    }

    @Test
    fun `invoke - 空文字のとき、適切なエラーメッセージを返すこと`() {
        val result = useCase("")
        assertTrue(result is ValidateGitHubUserNameUseCase.Result.Error)
        assertEquals(
            "ユーザー名を入力してください",
            (result as ValidateGitHubUserNameUseCase.Result.Error).message
        )
    }

    @Test
    fun `invoke - 不正な形式のとき、適切なエラーメッセージを返すこと`() {
        val invalidNames = listOf(
            "-start-with-hyphen",
            "end-with-hyphen-",
            "double--hyphen",
            "contains.dot",
            "contains space",
            "a".repeat(40), // 39文字制限超過
            "ユーザー名", // 漢字
            "user-😊" // 絵文字
        )
        invalidNames.forEach { name ->
            val result = useCase(name)
            assertTrue(
                "Should be Error for $name",
                result is ValidateGitHubUserNameUseCase.Result.Error
            )
            assertEquals(
                "GitHubのユーザー名形式が正しくありません",
                (result as ValidateGitHubUserNameUseCase.Result.Error).message
            )
        }
    }
}
