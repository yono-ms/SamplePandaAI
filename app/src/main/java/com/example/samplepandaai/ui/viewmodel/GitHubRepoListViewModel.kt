package com.example.samplepandaai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplepandaai.domain.model.AppException
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.usecase.GetGitHubReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import javax.inject.Inject

/**
 * リポジトリ一覧画面の状態を表す Sealed Interface
 */
sealed interface GitHubRepoListUiState {
    object Loading : GitHubRepoListUiState
    data class Success(val repos: List<GitHubRepo>) : GitHubRepoListUiState
    data class Error(val message: String) : GitHubRepoListUiState
}

/**
 * リポジトリ一覧の取得と状態管理を行う ViewModel。
 * UseCase を介してドメインロジックを実行する。
 */
@HiltViewModel
class GitHubRepoListViewModel @Inject constructor(
    private val getGitHubReposUseCase: GetGitHubReposUseCase
) : ViewModel() {

    private val logger = LoggerFactory.getLogger(GitHubRepoListViewModel::class.java)

    private val _uiState = MutableStateFlow<GitHubRepoListUiState>(GitHubRepoListUiState.Loading)
    val uiState: StateFlow<GitHubRepoListUiState> = _uiState.asStateFlow()

    /**
     * 指定されたユーザーのリポジトリを取得し、UiState を更新する
     */
    fun fetchRepositories(username: String) {
        viewModelScope.launch {
            logger.debug("Fetching repositories for user: {}", username)
            _uiState.value = GitHubRepoListUiState.Loading
            try {
                // UseCase を呼び出し（ドメイン層でのソート済みリストが返る）
                val repos = getGitHubReposUseCase(username)
                _uiState.value = GitHubRepoListUiState.Success(repos)
                logger.info("Successfully fetched {} repositories via UseCase", repos.size)
            } catch (e: AppException) {
                logger.error("Domain error occurred: ${e.message}", e)
                val errorMessage = when (e) {
                    is AppException.NetworkException -> "Network error. Please check your connection."
                    is AppException.ApiException -> "Server error (${e.code}). Please try again later."
                    is AppException.UnknownException -> e.message ?: "An unknown error occurred."
                }
                _uiState.value = GitHubRepoListUiState.Error(errorMessage)
            } catch (e: Exception) {
                logger.error("Unexpected error occurred", e)
                _uiState.value = GitHubRepoListUiState.Error("An unexpected error occurred.")
            }
        }
    }
}
