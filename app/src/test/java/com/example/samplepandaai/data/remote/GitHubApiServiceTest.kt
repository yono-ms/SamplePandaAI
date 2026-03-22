package com.example.samplepandaai.data.remote

import com.example.samplepandaai.TestUtils
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.slf4j.LoggerFactory

class GitHubApiServiceTest {
    private val logger = LoggerFactory.getLogger(GitHubApiServiceTest::class.java)

    @Test
    fun `listUserRepos returns a list of repositories on success`() = runBlocking {
        // 1. 外部ファイルから JSON を読み込む
        val mockJson = TestUtils.readResourceFile("github_repos_success.json")

        // 2. MockEngine の準備
        val mockEngine = MockEngine { _ ->
            respond(
                content = mockJson,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // 3. HttpClientFactory を使用してテスト用クライアントを作成
        val client = HttpClientFactory.create(mockEngine)
        val apiService = GitHubApiService(client)

        // 4. 実行
        val repos = apiService.listUserRepos("user")

        // 5. 内容をログに出力して確認
        logger.info("Fetched {} repositories.", repos.size)
        repos.forEach { repo ->
            logger.info(
                "Repo: name={}, stars={}, lang={}, url={}",
                repo.name, repo.stargazersCount, repo.language, repo.htmlUrl
            )
        }

        // 6. 検証
        assertEquals(1, repos.size)
        assertEquals("sample-repo", repos[0].name)
        assertEquals(99, repos[0].stargazersCount)
    }
}
