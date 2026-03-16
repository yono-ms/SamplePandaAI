package com.example.samplepandaai.ui.viewmodel

import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.usecase.GetGitHubReposUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Instant
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GitHubRepoListViewModelTest {

    private lateinit var getGitHubReposUseCase: GetGitHubReposUseCase
    private lateinit var viewModel: GitHubRepoListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // ViewModel の viewModelScope は Dispatchers.Main を使用するため、テスト用に差し替える
        Dispatchers.setMain(testDispatcher)

        getGitHubReposUseCase = mockk()
        viewModel = GitHubRepoListViewModel(getGitHubReposUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchRepositories should update uiState to Success when UseCase succeeds`() = runTest {
        // Arrange
        val mockRepos = listOf(
            createMockRepo(1, "repo1", 10),
            createMockRepo(2, "repo2", 20)
        )
        coEvery { getGitHubReposUseCase("user") } returns mockRepos

        // Act
        viewModel.fetchRepositories("user")

        // ローディング状態を確認 (最初の状態)
        assertTrue(viewModel.uiState.value is GitHubRepoListUiState.Loading)

        // コルーチンの実行を完了させる
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is GitHubRepoListUiState.Success)
        assertEquals(2, (currentState as GitHubRepoListUiState.Success).repos.size)
        assertEquals("repo1", currentState.repos[0].name)
    }

    @Test
    fun `fetchRepositories should update uiState to Error when UseCase fails`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { getGitHubReposUseCase("user") } throws Exception(errorMessage)

        // Act
        viewModel.fetchRepositories("user")
        advanceUntilIdle()

        // Assert
        val currentState = viewModel.uiState.value
        assertTrue(currentState is GitHubRepoListUiState.Error)
        assertEquals(errorMessage, (currentState as GitHubRepoListUiState.Error).message)
    }

    private fun createMockRepo(id: Long, name: String, stars: Int): GitHubRepo {
        return GitHubRepo(
            id = id,
            name = name,
            fullName = "user/$name",
            description = null,
            htmlUrl = "https://github.com/user/$name",
            stars = stars,
            language = "Kotlin",
            updatedAt = Instant.parse("2024-01-01T00:00:00Z")
        )
    }
}
