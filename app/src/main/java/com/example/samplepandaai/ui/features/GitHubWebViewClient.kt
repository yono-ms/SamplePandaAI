package com.example.samplepandaai.ui.features

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.samplepandaai.domain.usecase.IsGitHubDomainUseCase

/**
 * GitHubドメインへのアクセスを許可し、それ以外を外部ブラウザに委譲するWebViewClient
 */
class GitHubWebViewClient(
    private val context: Context,
    private val isGitHubDomainUseCase: IsGitHubDomainUseCase,
    private val onExternalUrlLaunched: () -> Unit = {}
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val requestUrl = request?.url ?: return false
        return handleUrl(requestUrl)
    }

    /**
     * URLのハンドリングロジック。
     */
    fun handleUrl(url: Uri): Boolean {
        val urlStr = url.toString()

        return if (isGitHubDomainUseCase(urlStr)) {
            false // WebView 内で読み込む
        } else {
            val intent = Intent(Intent.ACTION_VIEW, url).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            onExternalUrlLaunched()
            true // 読み込みをキャンセル
        }
    }
}
