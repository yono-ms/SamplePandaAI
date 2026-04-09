@file:Suppress("NonAsciiCharacters")

package com.example.samplepandaai.ui.viewmodel

import com.example.samplepandaai.domain.usecase.AddUserNameToHistoryUseCase
import com.example.samplepandaai.domain.usecase.ValidateGitHubUserNameUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserNameInputViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val validateUseCase: ValidateGitHubUserNameUseCase = mockk()
    private val addHistoryUseCase: AddUserNameToHistoryUseCase = mockk()
    private lateinit var viewModel: UserNameInputViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserNameInputViewModel(validateUseCase, addHistoryUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSubmit - UseCaseがInvalidFormatエラーを返したとき、errorTypeにInvalidFormatがセットされること`() =
        runTest {
        val name = "invalid-name"
            val expectedError = ValidateGitHubUserNameUseCase.Result.Error.InvalidFormat
            every { validateUseCase(name) } returns expectedError

        viewModel.onUserNameChanged(name)
        viewModel.onSubmit { }

            assertEquals(expectedError, viewModel.errorType.value)
            coVerify(exactly = 0) { addHistoryUseCase(any()) }
        }

    @Test
    fun `onSubmit - UseCaseがEmptyエラーを返したとき、errorTypeにEmptyがセットされること`() =
        runTest {
            val name = ""
            val expectedError = ValidateGitHubUserNameUseCase.Result.Error.Empty
            every { validateUseCase(name) } returns expectedError

            viewModel.onUserNameChanged(name)
            viewModel.onSubmit { }

            assertEquals(expectedError, viewModel.errorType.value)
        coVerify(exactly = 0) { addHistoryUseCase(any()) }
    }

    @Test
    fun `onSubmit - UseCaseが成功を返したとき、履歴保存UseCaseが呼ばれ成功コールバックが実行されること`() =
        runTest {
            val name = "valid-user"
            every { validateUseCase(name) } returns ValidateGitHubUserNameUseCase.Result.Success
            coEvery { addHistoryUseCase(name) } returns Unit

            var capturedName: String? = null
            viewModel.onUserNameChanged(name)
            viewModel.onSubmit { capturedName = it }

            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(name, capturedName)
            assertNull(viewModel.errorType.value)
            coVerify { addHistoryUseCase(name) }
        }
}
