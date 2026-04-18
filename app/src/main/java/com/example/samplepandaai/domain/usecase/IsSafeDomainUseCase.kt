package com.example.samplepandaai.domain.usecase

import javax.inject.Inject

/**
 * 与えられた URL が許可されたドメインかつ安全なスキームに属するかを判定する UseCase
 */
class IsSafeDomainUseCase @Inject constructor() {

    private val allowedDomains = listOf(
        "github.com",
        "raw.githubusercontent.com"
    )

    /**
     * URL 文字列を受け取り、スキームが https かつホスト名が許可されたドメインまたはそのサブドメインであるか判定する
     *
     * @param urlStr 判定対象の URL 文字列
     * @return 許可されたドメインかつ安全なスキームであれば true
     */
    operator fun invoke(urlStr: String?): Boolean {
        if (urlStr.isNullOrBlank()) return false

        return try {
            // スキームのチェック (https のみ許可)
            if (!urlStr.startsWith("https://", ignoreCase = true)) {
                return false
            }

            val host = extractHost(urlStr) ?: return false

            allowedDomains.any { domain ->
                host == domain || host.endsWith(".$domain")
            }
        } catch (_: Exception) {
            false
        }
    }

    /**
     * 簡易的なホスト名抽出ロジック (Android の Uri クラスに依存しない)
     */
    private fun extractHost(url: String): String? {
        val schemeDelimiter = "://"
        val startIndex = url.indexOf(schemeDelimiter).let {
            if (it == -1) 0 else it + schemeDelimiter.length
        }

        val remaining = url.substring(startIndex)
        val endIndex = remaining.indexOfAny(charArrayOf('/', '?', '#'))

        val hostWithPort = if (endIndex == -1) remaining else remaining.substring(0, endIndex)

        // ポート番号が含まれる場合は除外
        return hostWithPort.split(':')[0].ifBlank { null }
    }
}
