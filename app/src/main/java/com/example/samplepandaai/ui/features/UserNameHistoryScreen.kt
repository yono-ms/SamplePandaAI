package com.example.samplepandaai.ui.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.UserNameHistoryViewModel

@Composable
fun UserNameHistoryScreen(
    onNavigateToRepoList: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: UserNameHistoryViewModel = hiltViewModel()
) {
    val history by viewModel.history.collectAsState()

    UserNameHistoryContent(
        history = history,
        onDeleteUserName = { viewModel.onDeleteUserName(it) },
        onNavigateToRepoList = onNavigateToRepoList,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameHistoryContent(
    history: List<String>,
    onDeleteUserName: (String) -> Unit,
    onNavigateToRepoList: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("検索履歴") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "戻る"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "履歴はありません",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(history) { userName ->
                    ListItem(
                        headlineContent = { Text(userName) },
                        trailingContent = {
                            IconButton(onClick = { onDeleteUserName(userName) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "削除"
                                )
                            }
                        },
                        modifier = Modifier.clickable { onNavigateToRepoList(userName) }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserNameHistoryPreview() {
    SamplePandaAITheme {
        UserNameHistoryContent(
            history = listOf("google", "android", "kotlin"),
            onDeleteUserName = {},
            onNavigateToRepoList = {},
            onBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserNameHistoryPreview_Empty() {
    SamplePandaAITheme {
        UserNameHistoryContent(
            history = emptyList(),
            onDeleteUserName = {},
            onNavigateToRepoList = {},
            onBack = {}
        )
    }
}
