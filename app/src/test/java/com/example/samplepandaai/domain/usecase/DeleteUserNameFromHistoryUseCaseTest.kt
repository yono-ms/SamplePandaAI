package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DeleteUserNameFromHistoryUseCaseTest {
    private val repository: UserNameRepository = mockk()
    private val useCase = DeleteUserNameFromHistoryUseCase(repository)

    @Test
    fun `invoke - リポジトリの削除メソッドが呼ばれること`() = runTest {
        val userName = "test-user"
        coEvery { repository.deleteUserNameFromHistory(userName) } returns Unit

        useCase(userName)

        coVerify { repository.deleteUserNameFromHistory(userName) }
    }
}
