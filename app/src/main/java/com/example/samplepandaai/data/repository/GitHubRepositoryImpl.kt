package com.example.samplepandaai.data.repository

import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.domain.model.AppException
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.repository.GitHubRepository
import io.github.yono_ms.model.models.Repository
import io.ktor.client.plugins.ResponseException
import org.slf4j.LoggerFactory
import java.io.IOException
import kotlin.time.toKotlinInstant

/**
 * GitHubRepository の実装クラス。
 * API Service から取得した DTO を Domain Model へ変換する責務を持つ。
 */
class GitHubRepositoryImpl(
    private val apiService: GitHubApiService
) : GitHubRepository {

    private val logger = LoggerFactory.getLogger(GitHubRepositoryImpl::class.java)

    override suspend fun getUserRepositories(username: String): List<GitHubRepo> {
        logger.debug("Fetching repositories for user: {}", username)
        return try {
            val dtos = apiService.listUserRepos(username)
            dtos.map { it.toDomainModel() }
        } catch (e: ResponseException) {
            logger.error("API error occurred: ${e.response.status}", e)
            throw AppException.ApiException(
                code = e.response.status.value,
                message = "API error: ${e.response.status.description}",
                cause = e
            )
        } catch (e: IOException) {
            logger.error("Network error occurred", e)
            throw AppException.NetworkException("Network connection error", e)
        } catch (e: Exception) {
            if (e is AppException) throw e
            logger.error("Unknown error occurred", e)
            throw AppException.UnknownException(e.message ?: "Unknown error", e)
        }
    }

    /**
     * DTO (OpenAPI 生成) から Domain Model への変換。
     */
    private fun Repository.toDomainModel(): GitHubRepo {
        return GitHubRepo(
            id = this.id,
            name = this.name,
            fullName = this.fullName,
            description = this.description,
            htmlUrl = this.htmlUrl.toString(),
            stars = this.stargazersCount,
            language = this.language,
            updatedAt = try {
                // DTO の updatedAt は java.time.OffsetDateTime (カスタムシリアライザーにより)
                // domain model では kotlinx.datetime.Instant を使用するため変換する
                this.updatedAt.toInstant().toKotlinInstant()
            } catch (e: Exception) {
                logger.warn("Failed to parse updatedAt for repo {}: {}", this.name, e.message)
                kotlin.time.Instant.DISTANT_PAST
            }
        )
    }
}
