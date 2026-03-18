package com.example.samplepandaai.domain.model

/**
 * アプリケーション固有の例外を表す基本クラス。
 * Data層の例外（Ktorなど）をDomain層の例外に変換するために使用する。
 */
sealed class AppException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    /**
     * ネットワーク接続エラー（オフライン、タイムアウト等）
     */
    class NetworkException(message: String, cause: Throwable? = null) : AppException(message, cause)

    /**
     * API エラー（HTTP 4xx, 5xx）
     */
    class ApiException(val code: Int, message: String, cause: Throwable? = null) :
        AppException(message, cause)

    /**
     * 不明なエラー
     */
    class UnknownException(message: String, cause: Throwable? = null) : AppException(message, cause)
}
