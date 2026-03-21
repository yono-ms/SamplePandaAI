package com.example.samplepandaai.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.UserNameInputViewModel

/**
 * ユーザー名入力画面 (Stateful)
 * マニュアル推奨の Type-safe Navigation (object/class) は MainActivity の NavHost で適用済み。
 */
@Composable
fun UserNameInputScreen(
    onNavigateToRepoList: (String) -> Unit,
    onNavigateToHistory: () -> Unit,
    viewModel: UserNameInputViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    UserNameInputContent(
        userName = userName,
        errorMessage = errorMessage,
        onUserNameChanged = { viewModel.onUserNameChanged(it) },
        onSubmit = { viewModel.onSubmit(onNavigateToRepoList) },
        onNavigateToHistory = onNavigateToHistory
    )
}

/**
 * ユーザー名入力画面のコンテンツ (Stateless)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameInputContent(
    userName: String,
    errorMessage: String?,
    onUserNameChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GitHub Repo Explorer") },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "履歴を表示"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ユーザー名を入力してください",
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // フィールドの構成を修正: 
            // 1. タイトルを Text として独立させ、線との重なりを回避。
            // 2. label の代わりに入力中のみ表示される placeholder を使用。
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "GitHub ユーザー名",
                    style = MaterialTheme.typography.labelLarge,
                    color = if (errorMessage != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = userName,
                    onValueChange = onUserNameChanged,
                    placeholder = { Text("例: google") }, // 入力すると消えるヒント
                    isError = errorMessage != null,
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    supportingText = {
                        // 公式推奨の supportingText を使用し、独立したメッセージ表示を行う
                        if (errorMessage != null) {
                            Text(text = errorMessage)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text("リポジトリを取得する")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserNameInputPreview() {
    SamplePandaAITheme {
        UserNameInputContent(
            userName = "",
            errorMessage = null,
            onUserNameChanged = {},
            onSubmit = {},
            onNavigateToHistory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UserNameInputPreview_Error() {
    SamplePandaAITheme {
        UserNameInputContent(
            userName = "invalid--name",
            errorMessage = "GitHubのユーザー名形式が正しくありません",
            onUserNameChanged = {},
            onSubmit = {},
            onNavigateToHistory = {}
        )
    }
}
