@file:Suppress("NonAsciiCharacters")

package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddUserNameToHistoryUseCaseTest {
    private val repository: UserNameRepository = mockk()
    private val useCase = AddUserNameToHistoryUseCase(repository)

    @Test
    fun `invoke - リポジトリの追加メソッドが呼ばれること`() = runTest {
        val userName = "test-user"
        coEvery { repository.addUserNameToHistory(userName) } returns Unit

        useCase(userName)

        coVerify { repository.addUserNameToHistory(userName) }
    }
}
