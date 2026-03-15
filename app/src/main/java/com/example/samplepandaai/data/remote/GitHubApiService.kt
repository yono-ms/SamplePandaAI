package com.example.samplepandaai.data.remote

import com.example.samplepandaai.data.remote.dto.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.slf4j.LoggerFactory

/**
 * GitHub API との通信を担うサービス。
 * HttpClient をコンストラクタで受け取ることで、試験時に MockEngine 等への差し替えを可能にする。
 */
class GitHubApiService(
    private val client: HttpClient
) {
    private val logger = LoggerFactory.getLogger(GitHubApiService::class.java)

    companion object {
        private const val BASE_URL = "https://api.github.com"
    }

    /**
     * 指定されたユーザーのリポジトリリストを取得する。
     */
    suspend fun listUserRepos(username: String): List<Repository> {
        logger.debug("Requesting repos for user: {}", username)
        return try {
            val response = client.get("$BASE_URL/users/$username/repos")
            response.body<List<Repository>>()
        } catch (e: Exception) {
            logger.error("Failed to fetch repos for user: $username", e)
            throw e
        }
    }
}
