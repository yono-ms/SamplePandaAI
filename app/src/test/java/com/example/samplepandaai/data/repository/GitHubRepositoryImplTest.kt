package com.example.samplepandaai.data.repository

import com.example.samplepandaai.TestUtils
import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.domain.model.AppException
import com.example.samplepandaai.domain.repository.GitHubRepository
import com.example.samplepandaai.util.serialization.OffsetDateTimeKSerializer
import com.example.samplepandaai.util.serialization.URIKSerializer
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.URI
import java.time.OffsetDateTime

class GitHubRepositoryImplTest {

    private lateinit var repository: GitHubRepository

    @Before
    fun setUp() {
        val mockEngine = MockEngine { request ->
            when (request.url.encodedPath) {
                "/users/testuser/repos" -> {
                    val mockJson = TestUtils.readResourceFile("github_repos_success.json")
                    respond(
                        content = mockJson,
                        status = HttpStatusCode.OK,
                        headers = headersOf(
                            HttpHeaders.ContentType,
                            ContentType.Application.Json.toString()
                        )
                    )
                }

                else -> respond("", HttpStatusCode.NotFound)
            }
        }

        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    serializersModule = SerializersModule {
                        contextual(URI::class, URIKSerializer)
                        contextual(OffsetDateTime::class, OffsetDateTimeKSerializer)
                    }
                })
            }
        }

        val apiService = GitHubApiService(client)
        repository = GitHubRepositoryImpl(apiService)
    }

    @Test
    fun `getUserRepositories should return mapped domain models`() = runBlocking {
        // Act
        val result = repository.getUserRepositories("testuser")

        // Assert
        assertEquals(1, result.size)
        val repo = result[0]
        assertEquals(12345L, repo.id)
        assertEquals("sample-repo", repo.name)
        assertEquals("owner/sample-repo", repo.fullName)
        assertEquals("This is a sample repository", repo.description)
        assertEquals("https://github.com/owner/sample-repo", repo.htmlUrl)
        assertEquals(99, repo.stars)
        assertEquals("Kotlin", repo.language)
        assertEquals("2024-01-02T10:00:00Z", repo.updatedAt.toString())
    }

    @Test
    fun `getUserRepositories should return empty list when user has no repos`() = runBlocking {
        // Arrange
        val emptyMockEngine = MockEngine {
            val mockJson = TestUtils.readResourceFile("github_repos_empty.json")
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(emptyMockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                    serializersModule = SerializersModule {
                        contextual(URI::class, URIKSerializer)
                        contextual(OffsetDateTime::class, OffsetDateTimeKSerializer)
                    }
                })
            }
        }
        val repo = GitHubRepositoryImpl(GitHubApiService(client))

        // Act
        val result = repo.getUserRepositories("emptyuser")

        // Assert
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getUserRepositories should throw ApiException when API returns 404`() = runBlocking {
        // Arrange
        val errorMockEngine = MockEngine {
            respond(
                content = "Not Found",
                status = HttpStatusCode.NotFound,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Text.Plain.toString())
            )
        }
        val client = HttpClient(errorMockEngine) {
            install(ContentNegotiation) { json() }
        }
        val repo = GitHubRepositoryImpl(GitHubApiService(client))

        // Act & Assert
        try {
            repo.getUserRepositories("nonexistentuser")
            org.junit.Assert.fail("Expected AppException.ApiException was not thrown")
        } catch (e: AppException.ApiException) {
            assertEquals(HttpStatusCode.NotFound.value, e.code)
        }
    }

    @Test
    fun `getUserRepositories should throw NetworkException when IO error occurs`() = runBlocking {
        // Arrange
        val apiService = io.mockk.mockk<GitHubApiService>()
        io.mockk.coEvery { apiService.listUserRepos(any()) } throws java.io.IOException("No internet")
        val repo = GitHubRepositoryImpl(apiService)

        // Act & Assert
        try {
            repo.getUserRepositories("testuser")
            org.junit.Assert.fail("Expected AppException.NetworkException was not thrown")
        } catch (e: AppException.NetworkException) {
            assertEquals("Network connection error", e.message)
        }
    }
}
