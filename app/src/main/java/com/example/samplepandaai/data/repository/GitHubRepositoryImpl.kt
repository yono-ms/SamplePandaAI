package com.example.samplepandaai.data.repository

import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.remote.dto.Repository
import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.repository.GitHubRepository
import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import org.slf4j.LoggerFactory

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
        val dtos = apiService.listUserRepos(username)
        return dtos.map { it.toDomainModel() }
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
                Instant.DISTANT_PAST
            }
        )
    }
}
