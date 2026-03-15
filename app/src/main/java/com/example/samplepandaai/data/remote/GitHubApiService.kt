package com.example.samplepandaai.data.remote

import com.example.samplepandaai.data.remote.dto.Repository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.slf4j.LoggerFactory

/**
 * GitHub API との通信を担うサービス。
 * HttpClient に設定されたデフォルトの BASE_URL を使用する。
 */
class GitHubApiService(
    private val client: HttpClient
) {
    private val logger = LoggerFactory.getLogger(GitHubApiService::class.java)

    /**
     * 指定されたユーザーのリポジトリリストを取得する。
     */
    suspend fun listUserRepos(username: String): List<Repository> {
        logger.debug("Requesting repos for user: {}", username)
        return try {
            // HttpClientFactory で設定された BASE_URL が自動的に付与される
            val response = client.get("users/$username/repos")
            response.body<List<Repository>>()
        } catch (e: Exception) {
            logger.error("Failed to fetch repos for user: $username", e)
            throw e
        }
    }
}
