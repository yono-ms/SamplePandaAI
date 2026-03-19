package com.example.samplepandaai.data.repository

import com.example.samplepandaai.TestUtils
import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.remote.HttpClientFactory
import com.example.samplepandaai.domain.repository.GitHubRepository
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.slf4j.LoggerFactory

/**
 * MockWebServer を使用した、実際のネットワークスタックを通したリポジトリのテスト。
 */
class GitHubRepositoryMockWebServerTest {

    private val logger = LoggerFactory.getLogger(GitHubRepositoryMockWebServerTest::class.java)
    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: GitHubRepository

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val baseUrl = mockWebServer.url("/").toString()
        logger.info("MockWebServer started at: {}", baseUrl)

        val client = HttpClientFactory.create(OkHttp.create()).config {
            defaultRequest {
                url(baseUrl)
            }
        }

        val apiService = GitHubApiService(client)
        repository = GitHubRepositoryImpl(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getUserRepositories should handle 404 Not Found`() = runBlocking {
        val mockJson = TestUtils.readResourceFile("github_error_not_found.json")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(404)
                .addHeader("Content-Type", "application/json")
                .setBody(mockJson)
        )

        try {
            repository.getUserRepositories("non-existent-user")
            assertTrue("Should throw an exception for 404", false)
        } catch (e: Exception) {
            logger.info("Caught expected exception for 404: {}", e.message)
        }
    }

    @Test
    fun `getUserRepositories should handle 500 Internal Server Error`() = runBlocking {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500)
                .setBody("Internal Server Error")
        )

        try {
            repository.getUserRepositories("any-user")
            assertTrue("Should throw an exception for 500", false)
        } catch (e: Exception) {
            logger.info("Caught expected exception for 500: {}", e.message)
        }
    }

    @Test
    fun `getUserRepositories should handle malformed JSON`() = runBlocking {
        val mockJson = TestUtils.readResourceFile("github_repos_malformed.json")
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(mockJson)
        )

        try {
            repository.getUserRepositories("any-user")
            assertTrue("Should throw an exception for malformed JSON", false)
        } catch (e: Exception) {
            logger.info("Caught expected exception for malformed JSON: {}", e.message)
        }
    }

    @Test
    fun `getUserRepositories should succeed with valid response from server`() = runBlocking {
        // Arrange
        // 外部ファイルから JSON を読み込む
        val mockJson = TestUtils.readResourceFile("github_repos_success.json")

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(mockJson)
        )

        // Act
        val result = repository.getUserRepositories("owner")

        // Assert
        assertEquals(1, result.size)
        assertEquals("sample-repo", result[0].name)
        assertEquals(99, result[0].stars)
        assertEquals("Kotlin", result[0].language)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/users/owner/repos", recordedRequest.path)
    }
}
