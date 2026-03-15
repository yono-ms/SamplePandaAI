package com.example.samplepandaai.ui.features

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.samplepandaai.ui.components.ErrorView
import com.example.samplepandaai.ui.components.LoadingView
import com.example.samplepandaai.ui.components.RepoListItem
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListUiState
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListScreen(
    viewModel: GitHubRepoListViewModel,
    username: String = "google", // デフォルトで google のリポジトリを表示
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    // 画面表示時にデータを取得する
    LaunchedEffect(username) {
        viewModel.fetchRepositories(username)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "GitHub Repositories: $username") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is GitHubRepoListUiState.Loading -> {
                    LoadingView()
                }
                is GitHubRepoListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(state.repos) { repo ->
                            RepoListItem(repo = repo)
                        }
                    }
                }
                is GitHubRepoListUiState.Error -> {
                    ErrorView(
                        message = state.message,
                        onRetry = { viewModel.fetchRepositories(username) }
                    )
                }
            }
        }
    }
}
