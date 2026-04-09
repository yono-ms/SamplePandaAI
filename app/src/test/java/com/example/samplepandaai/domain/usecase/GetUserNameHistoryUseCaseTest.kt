@file:Suppress("NonAsciiCharacters")

package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.repository.UserNameRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first // 追加
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest // 追加
import org.junit.Assert.assertEquals // 追加
import org.junit.Test

class GetUserNameHistoryUseCaseTest {
    private val repository: UserNameRepository = mockk()
    private val useCase = GetUserNameHistoryUseCase(repository)

    @Test
    fun `invoke - リポジトリの取得メソッドが呼ばれること`() = runTest { // runTest を追加
        val history = listOf("user1", "user2")
        every { repository.getUserNameHistory() } returns flowOf(history)

        // Flow から実際にデータを取り出す（これで内部処理が動き、警告が消える）
        val result = useCase().first()

        // リポジトリが呼ばれたことの検証
        verify {
            @Suppress("UnusedFlow")
            repository.getUserNameHistory()
        }

        // データが正しいことの検証（これこそが本来のテスト目的）
        assertEquals(history, result)
    }
}