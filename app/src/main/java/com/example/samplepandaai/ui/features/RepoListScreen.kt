package com.example.samplepandaai.ui.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.ui.components.ErrorView
import com.example.samplepandaai.ui.components.LoadingView
import com.example.samplepandaai.ui.components.RepoListItem
import com.example.samplepandaai.ui.theme.MultiLanguagePreview
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListUiState
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListViewModel
import kotlinx.datetime.Instant

/**
 * ViewModel に依存する Stateful な Composable。
 * 画面のエントリポイントとして使用する。
 */
@Composable
fun RepoListScreen(
    viewModel: GitHubRepoListViewModel,
    username: String = "google",
    onBack: () -> Unit = {},
    onRepoClick: (GitHubRepo) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(username) {
        viewModel.fetchRepositories(username)
    }

    RepoListContent(
        uiState = uiState,
        username = username,
        onBack = onBack,
        onRepoClick = onRepoClick,
        onRetry = { viewModel.fetchRepositories(username) },
        modifier = modifier
    )
}

/**
 * 状態 (UiState) にのみ依存する Stateless な Composable。
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoListContent(
    uiState: GitHubRepoListUiState,
    username: String,
    onBack: () -> Unit,
    onRepoClick: (GitHubRepo) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.repo_list_title, username))
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_content_description)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (uiState) {
                is GitHubRepoListUiState.Loading -> {
                    LoadingView()
                }
                is GitHubRepoListUiState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(uiState.repos) { repo ->
                            RepoListItem(
                                repo = repo,
                                onClick = { onRepoClick(repo) }
                            )
                        }
                    }
                }
                is GitHubRepoListUiState.Error -> {
                    ErrorView(
                        message = uiState.message,
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}

@MultiLanguagePreview
@Preview(showBackground = true)
@Composable
fun RepoListPreview_Success() {
    val mockRepos = listOf(
        GitHubRepo(
            id = 1,
            name = "ComposeSample",
            fullName = "google/ComposeSample",
            description = "A sample project for Jetpack Compose.",
            htmlUrl = "",
            stars = 1234,
            language = "Kotlin",
            updatedAt = Instant.parse("2024-01-01T00:00:00Z")
        ),
        GitHubRepo(
            id = 2,
            name = "AndroidArchitecture",
            fullName = "google/AndroidArchitecture",
            description = "Best practices for Android app architecture.",
            htmlUrl = "",
            stars = 5678,
            language = "Java",
            updatedAt = Instant.parse("2024-02-01T00:00:00Z")
        )
    )

    SamplePandaAITheme {
        RepoListContent(
            uiState = GitHubRepoListUiState.Success(mockRepos),
            username = "google",
            onBack = {},
            onRepoClick = {},
            onRetry = {}
        )
    }
}
