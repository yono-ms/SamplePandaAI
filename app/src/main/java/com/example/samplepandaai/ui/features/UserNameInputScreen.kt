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
import androidx.compose.material.icons.filled.Info
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.usecase.ValidateGitHubUserNameUseCase
import com.example.samplepandaai.ui.theme.MultiLanguagePreview
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
    onNavigateToLicense: () -> Unit,
    viewModel: UserNameInputViewModel = hiltViewModel()
) {
    val userName by viewModel.userName.collectAsState()
    val errorType by viewModel.errorType.collectAsState()

    UserNameInputContent(
        userName = userName,
        errorType = errorType,
        onUserNameChanged = { viewModel.onUserNameChanged(it) },
        onSubmit = { viewModel.onSubmit(onNavigateToRepoList) },
        onNavigateToHistory = onNavigateToHistory,
        onNavigateToLicense = onNavigateToLicense
    )
}

/**
 * ユーザー名入力画面のコンテンツ (Stateless)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserNameInputContent(
    userName: String,
    errorType: ValidateGitHubUserNameUseCase.Result.Error?,
    onUserNameChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onNavigateToHistory: () -> Unit,
    onNavigateToLicense: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.user_name_input_title)) },
                actions = {
                    IconButton(onClick = onNavigateToHistory) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = stringResource(R.string.user_name_history_icon_content_description)
                        )
                    }
                    IconButton(onClick = onNavigateToLicense) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = stringResource(R.string.license_menu_item)
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
                text = stringResource(R.string.user_name_input_instruction),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // MD3 準拠: label を OutlinedTextField の引数として渡し、枠線上のアニメーションを有効にする。
            // これにより androidTest の onNodeWithText("GitHub ユーザー名") が TextField を正しく見つけられるようになる。
            OutlinedTextField(
                value = userName,
                onValueChange = onUserNameChanged,
                label = { Text(stringResource(R.string.user_name_field_label)) },
                placeholder = { Text(stringResource(R.string.user_name_field_placeholder)) },
                isError = errorType != null,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                supportingText = {
                    // 公式推奨の supportingText を使用
                    if (errorType != null) {
                        val errorMessage = when (errorType) {
                            ValidateGitHubUserNameUseCase.Result.Error.Empty -> stringResource(R.string.error_empty_user_name)
                            ValidateGitHubUserNameUseCase.Result.Error.InvalidFormat -> stringResource(
                                R.string.error_invalid_user_name
                            )
                        }
                        Text(text = errorMessage)
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(stringResource(R.string.user_name_submit_button))
            }
        }
    }
}

@MultiLanguagePreview
@Preview(showBackground = true)
@Composable
fun UserNameInputPreview() {
    SamplePandaAITheme {
        UserNameInputContent(
            userName = "",
            errorType = null,
            onUserNameChanged = {},
            onSubmit = {},
            onNavigateToHistory = {},
            onNavigateToLicense = {}
        )
    }
}
