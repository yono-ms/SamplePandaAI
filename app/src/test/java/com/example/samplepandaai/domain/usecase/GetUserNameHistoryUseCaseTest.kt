package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class GetUserNameHistoryUseCaseTest {
    private val repository: UserNameRepository = mockk()
    private val useCase = GetUserNameHistoryUseCase(repository)

    @Test
    fun `invoke - リポジトリの取得メソッドが呼ばれること`() {
        val history = listOf("user1", "user2")
        every { repository.getUserNameHistory() } returns flowOf(history)

        useCase()

        verify { repository.getUserNameHistory() }
    }
}
