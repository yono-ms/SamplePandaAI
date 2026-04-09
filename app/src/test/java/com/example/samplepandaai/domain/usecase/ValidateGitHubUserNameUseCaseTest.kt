@file:Suppress("NonAsciiCharacters")

package com.example.samplepandaai.domain.usecase

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
    fun `invoke - 空文字のとき、Error_Emptyを返すこと`() {
        val result = useCase("")
        assertTrue(result is ValidateGitHubUserNameUseCase.Result.Error.Empty)
    }

    @Test
    fun `invoke - 不正な形式のとき、Error_InvalidFormatを返すこと`() {
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
                "Should be Error.InvalidFormat for $name",
                result is ValidateGitHubUserNameUseCase.Result.Error.InvalidFormat
            )
        }
    }
}
