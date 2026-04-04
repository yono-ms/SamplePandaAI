package com.example.samplepandaai.ui.features

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.samplepandaai.domain.usecase.IsSafeDomainUseCase

/**
 * 許可されたドメインへのアクセスを維持し、それ以外を外部ブラウザに委譲するWebViewClient
 */
class SafeWebViewClient(
    private val context: Context,
    private val isSafeDomainUseCase: IsSafeDomainUseCase,
    private val onExternalUrlLaunched: () -> Unit = {}
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val requestUrl = request?.url ?: return false
        return handleUrl(requestUrl)
    }

    /**
     * URLのハンドリングロジック。
     * UseCase を使用して判定を行い、許可されない場合は外部ブラウザを起動する。
     */
    fun handleUrl(url: Uri): Boolean {
        val urlStr = url.toString()

        return if (isSafeDomainUseCase(urlStr)) {
            false // WebView 内で読み込む
        } else {
            val intent = Intent(Intent.ACTION_VIEW, url).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            try {
                context.startActivity(intent)
                onExternalUrlLaunched()
            } catch (e: Exception) {
                // インテント発行失敗時は何もしない（またはログ出力）
            }
            true // 読み込みをキャンセル
        }
    }
}
