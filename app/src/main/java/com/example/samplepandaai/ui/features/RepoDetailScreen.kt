package com.example.samplepandaai.ui.features

import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import com.example.samplepandaai.R
import com.example.samplepandaai.domain.usecase.IsSafeDomainUseCase
import com.example.samplepandaai.ui.theme.MultiLanguagePreview
import com.example.samplepandaai.ui.theme.SamplePandaAITheme

/**
 * リポジトリ詳細画面 (Stateful)
 *
 * @param url 表示対象の GitHub URL
 * @param title 画面上部に表示するタイトル
 * @param onBackClick 戻るボタンタップ時の処理
 * @param isSafeDomainUseCase ドメイン判定用 UseCase
 */
@Composable
fun RepoDetailScreen(
    url: String,
    title: String,
    onBackClick: () -> Unit,
    isSafeDomainUseCase: IsSafeDomainUseCase,
    modifier: Modifier = Modifier
) {
    RepoDetailContent(
        url = url,
        title = title,
        onBackClick = onBackClick,
        isSafeDomainUseCase = isSafeDomainUseCase,
        modifier = modifier
    )
}

/**
 * リポジトリ詳細画面 (Stateless)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoDetailContent(
    url: String,
    title: String,
    onBackClick: () -> Unit,
    isSafeDomainUseCase: IsSafeDomainUseCase,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = title) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_content_description)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (LocalInspectionMode.current) {
            // Preview モードでは WebView がクラッシュするため、プレースホルダーを表示する
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "WebView Preview (URL: $url)")
            }
        } else {
            AndroidView(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                factory = { ctx ->
                    WebView(ctx).apply {
                        webViewClient = SafeWebViewClient(
                            context = ctx,
                            isSafeDomainUseCase = isSafeDomainUseCase
                        )
                        settings.apply {
                            // セキュリティ設定: 必要最小限の権限のみ付与
                            javaScriptEnabled = true // GitHub ページの正常な表示に必要
                            domStorageEnabled = true

                            // ローカルファイルへのアクセスを禁止 (セキュリティ強化)
                            allowFileAccess = false
                            allowContentAccess = false
                            @Suppress("DEPRECATION")
                            allowFileAccessFromFileURLs = false
                            @Suppress("DEPRECATION")
                            allowUniversalAccessFromFileURLs = false

                            loadWithOverviewMode = true
                            useWideViewPort = true
                        }
                    }
                },
                update = { webView ->
                    webView.loadUrl(url)
                }
            )
        }
    }
}

@MultiLanguagePreview
@Composable
fun RepoDetailPreview() {
    SamplePandaAITheme {
        RepoDetailContent(
            url = "https://github.com/example/repo",
            title = "Example Repo",
            onBackClick = {},
            isSafeDomainUseCase = IsSafeDomainUseCase()
        )
    }
}
