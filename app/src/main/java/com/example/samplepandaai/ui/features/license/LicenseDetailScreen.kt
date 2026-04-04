package com.example.samplepandaai.ui.features.license

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
import com.example.samplepandaai.ui.features.SafeWebViewClient
import com.example.samplepandaai.ui.theme.MultiLanguagePreview
import com.example.samplepandaai.ui.theme.SamplePandaAITheme

/**
 * ライセンス詳細画面 (Stateful)
 *
 * @param url 表示対象のライセンス URL
 * @param title 画面上部に表示するタイトル
 * @param onBackClick 戻るボタンタップ時の処理
 * @param isSafeDomainUseCase ドメイン判定用 UseCase
 */
@Composable
fun LicenseDetailScreen(
    url: String,
    title: String,
    onBackClick: () -> Unit,
    isSafeDomainUseCase: IsSafeDomainUseCase,
    modifier: Modifier = Modifier
) {
    LicenseDetailContent(
        url = url,
        title = title,
        onBackClick = onBackClick,
        isSafeDomainUseCase = isSafeDomainUseCase,
        modifier = modifier
    )
}

/**
 * ライセンス詳細画面 (Stateless)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicenseDetailContent(
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
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "License WebView Preview (URL: $url)")
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
                            javaScriptEnabled = true
                            domStorageEnabled = true
                            allowFileAccess = false
                            allowContentAccess = false
                            allowFileAccessFromFileURLs = false
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
fun LicenseDetailPreview() {
    SamplePandaAITheme {
        LicenseDetailContent(
            url = "https://www.apache.org/licenses/LICENSE-2.0",
            title = "Apache License 2.0",
            onBackClick = {},
            isSafeDomainUseCase = IsSafeDomainUseCase()
        )
    }
}
