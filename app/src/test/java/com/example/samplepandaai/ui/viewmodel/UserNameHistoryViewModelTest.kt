package com.example.samplepandaai.ui.viewmodel

import com.example.samplepandaai.domain.usecase.DeleteUserNameFromHistoryUseCase
import com.example.samplepandaai.domain.usecase.GetUserNameHistoryUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserNameHistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val getUserNameHistoryUseCase: GetUserNameHistoryUseCase = mockk()
    private val deleteUserNameFromHistoryUseCase: DeleteUserNameFromHistoryUseCase = mockk()
    private lateinit var viewModel: UserNameHistoryViewModel

    // テスト用の動的な履歴データ
    private val fakeHistoryFlow = MutableStateFlow(listOf("user1", "user2"))

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        // 履歴取得 UseCase はこの動的な Flow を返すように設定
        every { getUserNameHistoryUseCase() } returns fakeHistoryFlow

        viewModel =
            UserNameHistoryViewModel(getUserNameHistoryUseCase, deleteUserNameFromHistoryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `history - 初期状態でリポジトリの履歴が反映されていること`() = runTest {
        // WhileSubscribed(5000) を使用しているため、collect して Flow をアクティブにする必要がある
        val collectJob = backgroundScope.launch(testDispatcher) {
            viewModel.history.collect {}
        }

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(listOf("user1", "user2"), viewModel.history.value)

        collectJob.cancel()
    }

    @Test
    fun `onDeleteUserName - 削除実行後、リストの状態が正しく更新されること`() = runTest {
        // WhileSubscribed(5000) を使用しているため、collect して Flow をアクティブにする必要がある
        val collectJob = backgroundScope.launch(testDispatcher) {
            viewModel.history.collect {}
        }

        val targetUser = "user1"

        // 削除 UseCase が呼ばれたら、fakeHistoryFlow の中身を更新するようにシミュレート
        coEvery { deleteUserNameFromHistoryUseCase(targetUser) } coAnswers {
            fakeHistoryFlow.value = fakeHistoryFlow.value.filter { it != targetUser }
        }

        // 削除アクション実行
        viewModel.onDeleteUserName(targetUser)

        testDispatcher.scheduler.advanceUntilIdle()

        // 1. UseCase が呼ばれたことの検証
        coVerify { deleteUserNameFromHistoryUseCase(targetUser) }

        // 2. リストの内容が更新されていることの検証
        assertEquals(listOf("user2"), viewModel.history.value)

        collectJob.cancel()
    }
}
