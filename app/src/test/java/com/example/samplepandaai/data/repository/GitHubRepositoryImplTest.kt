package com.example.samplepandaai.data.repository

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
                    respond(
                        content = """
                        [
                            {
                                "id": 1,
                                "node_id": "MDEwOlJlcG9zaXRvcnkx",
                                "name": "test-repo",
                                "full_name": "testuser/test-repo",
                                "private": false,
                                "owner": {
                                    "login": "testuser",
                                    "id": 100,
                                    "node_id": "MDQ6VXNlcjEwMA==",
                                    "avatar_url": "https://example.com/avatar.png",
                                    "gravatar_id": "",
                                    "url": "https://api.github.com/users/testuser",
                                    "html_url": "https://github.com/testuser",
                                    "followers_url": "https://api.github.com/users/testuser/followers",
                                    "following_url": "https://api.github.com/users/testuser/following",
                                    "gists_url": "https://api.github.com/users/testuser/gists",
                                    "starred_url": "https://api.github.com/users/testuser/starred",
                                    "subscriptions_url": "https://api.github.com/users/testuser/subscriptions",
                                    "organizations_url": "https://api.github.com/users/testuser/orgs",
                                    "repos_url": "https://api.github.com/users/testuser/repos",
                                    "events_url": "https://api.github.com/users/testuser/events",
                                    "received_events_url": "https://api.github.com/users/testuser/received_events",
                                    "type": "User",
                                    "site_admin": false
                                },
                                "html_url": "https://github.com/testuser/test-repo",
                                "description": "This is a test repository",
                                "fork": false,
                                "url": "https://api.github.com/users/testuser/repos",
                                "forks_url": "https://api.github.com/users/testuser/repos",
                                "keys_url": "https://api.github.com/users/testuser/repos",
                                "collaborators_url": "https://api.github.com/users/testuser/repos",
                                "teams_url": "https://api.github.com/users/testuser/repos",
                                "hooks_url": "https://api.github.com/users/testuser/repos",
                                "issue_events_url": "https://api.github.com/users/testuser/repos",
                                "events_url": "https://api.github.com/users/testuser/repos",
                                "assignees_url": "https://api.github.com/users/testuser/repos",
                                "branches_url": "https://api.github.com/users/testuser/repos",
                                "tags_url": "https://api.github.com/users/testuser/repos",
                                "blobs_url": "https://api.github.com/users/testuser/repos",
                                "git_tags_url": "https://api.github.com/users/testuser/repos",
                                "git_refs_url": "https://api.github.com/users/testuser/repos",
                                "trees_url": "https://api.github.com/users/testuser/repos",
                                "statuses_url": "https://api.github.com/users/testuser/repos",
                                "languages_url": "https://api.github.com/users/testuser/repos",
                                "stargazers_url": "https://api.github.com/users/testuser/repos",
                                "contributors_url": "https://api.github.com/users/testuser/repos",
                                "subscribers_url": "https://api.github.com/users/testuser/repos",
                                "subscription_url": "https://api.github.com/users/testuser/repos",
                                "commits_url": "https://api.github.com/users/testuser/repos",
                                "git_commits_url": "https://api.github.com/users/testuser/repos",
                                "comments_url": "https://api.github.com/users/testuser/repos",
                                "issue_comment_url": "https://api.github.com/users/testuser/repos",
                                "contents_url": "https://api.github.com/users/testuser/repos",
                                "compare_url": "https://api.github.com/users/testuser/repos",
                                "merges_url": "https://api.github.com/users/testuser/repos",
                                "archive_url": "https://api.github.com/users/testuser/repos",
                                "downloads_url": "https://api.github.com/users/testuser/repos",
                                "issues_url": "https://api.github.com/users/testuser/repos",
                                "pulls_url": "https://api.github.com/users/testuser/repos",
                                "milestones_url": "https://api.github.com/users/testuser/repos",
                                "notifications_url": "https://api.github.com/users/testuser/repos",
                                "labels_url": "https://api.github.com/users/testuser/repos",
                                "releases_url": "https://api.github.com/users/testuser/repos",
                                "deployments_url": "https://api.github.com/users/testuser/repos",
                                "created_at": "2023-01-01T00:00:00Z",
                                "updated_at": "2023-06-01T12:00:00Z",
                                "pushed_at": "2023-06-01T12:00:00Z",
                                "git_url": "git://github.com/testuser/test-repo.git",
                                "ssh_url": "git@github.com:testuser/test-repo.git",
                                "clone_url": "https://github.com/testuser/test-repo.git",
                                "svn_url": "https://github.com/testuser/test-repo",
                                "size": 1024,
                                "stargazers_count": 42,
                                "watchers_count": 42,
                                "language": "Kotlin",
                                "has_issues": true,
                                "has_projects": true,
                                "has_downloads": true,
                                "has_wiki": true,
                                "has_pages": false,
                                "forks_count": 5,
                                "mirror_url": null,
                                "archived": false,
                                "disabled": false,
                                "open_issues_count": 2,
                                "forks": 5,
                                "open_issues": 2,
                                "watchers": 42,
                                "default_branch": "main"
                            }
                        ]
                        """.trimIndent(),
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
        assertEquals(1L, repo.id)
        assertEquals("test-repo", repo.name)
        assertEquals("testuser/test-repo", repo.fullName)
        assertEquals("This is a test repository", repo.description)
        assertEquals("https://github.com/testuser/test-repo", repo.htmlUrl)
        assertEquals(42, repo.stars)
        assertEquals("Kotlin", repo.language)
        assertEquals("2023-06-01T12:00:00Z", repo.updatedAt.toString())
    }

    @Test
    fun `getUserRepositories should return empty list when user has no repos`() = runBlocking {
        // Arrange
        val emptyMockEngine = MockEngine {
            respond(
                content = "[]",
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
        // MockEngine では IOException を直接シミュレートするのが難しいため、
        // 意図的に不正なレスポンスを投げるか、Service 自体を MockK でモックする
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
