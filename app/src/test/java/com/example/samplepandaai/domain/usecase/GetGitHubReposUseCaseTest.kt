package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.repository.GitHubRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Instant
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetGitHubReposUseCaseTest {

    private lateinit var repository: GitHubRepository
    private lateinit var useCase: GetGitHubReposUseCase

    @Before
    fun setUp() {
        // MockK を使用してリポジトリのモックを作成
        repository = mockk()
        useCase = GetGitHubReposUseCase(repository)
    }

    @Test
    fun `invoke should return repositories sorted by stars descending`() = runBlocking {
        // Arrange: 順序がバラバラなテストデータを用意
        val repo1 = createMockRepo(id = 1, name = "repo1", stars = 10)
        val repo2 = createMockRepo(id = 2, name = "repo2", stars = 50)
        val repo3 = createMockRepo(id = 3, name = "repo3", stars = 30)

        coEvery { repository.getUserRepositories("user") } returns listOf(repo1, repo2, repo3)

        // Act: UseCase を実行
        val result = useCase("user")

        // Assert: スター数の多い順 (repo2 -> repo3 -> repo1) にソートされているか検証
        assertEquals(3, result.size)
        assertEquals("repo2", result[0].name) // 50 stars
        assertEquals("repo3", result[1].name) // 30 stars
        assertEquals("repo1", result[2].name) // 10 stars
    }

    @Test
    fun `invoke should return empty list when repository returns empty`() = runBlocking {
        // Arrange
        coEvery { repository.getUserRepositories("any") } returns emptyList()

        // Act
        val result = useCase("any")

        // Assert
        assertTrue(result.isEmpty())
    }

    private fun createMockRepo(id: Long, name: String, stars: Int): GitHubRepo {
        return GitHubRepo(
            id = id,
            name = name,
            fullName = "user/$name",
            description = null,
            htmlUrl = "https://github.com/user/$name",
            stars = stars,
            language = "Kotlin",
            updatedAt = Instant.parse("2024-01-01T00:00:00Z")
        )
    }
}
