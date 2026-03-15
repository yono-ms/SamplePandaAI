package com.example.samplepandaai.data.remote

import com.example.samplepandaai.BuildConfig
import com.example.samplepandaai.util.serialization.OffsetDateTimeKSerializer
import com.example.samplepandaai.util.serialization.URIKSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.slf4j.LoggerFactory
import java.net.URI
import java.time.OffsetDateTime

/**
 * 共通設定を適用した HttpClient を生成するファクトリ。
 */
object HttpClientFactory {
    private val logger = LoggerFactory.getLogger("HttpClient")

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            // Flavor ごとに定義された BASE_URL をデフォルト設定として適用
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }

            // JSON変換の設定
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    isLenient = true
                    serializersModule = SerializersModule {
                        contextual(URI::class, URIKSerializer)
                        contextual(OffsetDateTime::class, OffsetDateTimeKSerializer)
                    }
                })
            }

            // Ktor 内部ログを SLF4J 経由で出力
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        HttpClientFactory.logger.debug(message)
                    }
                }
                level = LogLevel.INFO
            }
        }
    }
}
