package com.example.samplepandaai.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory

/**
 * リポジトリ一覧画面の状態を表す Sealed Interface
 */
sealed interface GitHubRepoListUiState {
    object Loading : GitHubRepoListUiState
    data class Success(val repos: List<GitHubRepo>) : GitHubRepoListUiState
    data class Error(val message: String) : GitHubRepoListUiState
}

/**
 * リポジトリ一覧の取得と状態管理を行う ViewModel
 */
class GitHubRepoListViewModel(
    private val repository: GitHubRepository
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
                val repos = repository.getUserRepositories(username)
                _uiState.value = GitHubRepoListUiState.Success(repos)
                logger.info("Successfully fetched {} repositories", repos.size)
            } catch (e: Exception) {
                logger.error("Failed to fetch repositories for user: $username", e)
                _uiState.value = GitHubRepoListUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
