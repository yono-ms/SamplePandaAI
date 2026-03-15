package com.example.samplepandaai.data.remote

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
        // 1. MockEngine の準備: GitHub API が返す JSON レスポンスをシミュレート
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                [
                    {
                        "id": 1,
                        "node_id": "node1",
                        "name": "Repo1",
                        "full_name": "user/Repo1",
                        "private": false,
                        "owner": {
                            "login": "user",
                            "id": 100,
                            "node_id": "node_user",
                            "avatar_url": "https://example.com/avatar.png",
                            "gravatar_id": "",
                            "url": "https://api.github.com/users/user",
                            "html_url": "https://github.com/user",
                            "followers_url": "https://api.github.com/users/user/followers",
                            "following_url": "https://api.github.com/users/user/following{/other_user}",
                            "gists_url": "https://api.github.com/users/user/gists{/gist_id}",
                            "starred_url": "https://api.github.com/users/user/starred{/owner}{/repo}",
                            "subscriptions_url": "https://api.github.com/users/user/subscriptions",
                            "organizations_url": "https://api.github.com/users/user/orgs",
                            "repos_url": "https://api.github.com/users/user/repos",
                            "events_url": "https://api.github.com/users/user/events{/privacy}",
                            "received_events_url": "https://api.github.com/users/user/received_events",
                            "type": "User",
                            "site_admin": false
                        },
                        "html_url": "https://github.com/user/Repo1",
                        "description": "Test Repository",
                        "fork": false,
                        "url": "https://api.github.com/repos/user/Repo1",
                        "forks_url": "https://api.github.com/repos/user/Repo1/forks",
                        "keys_url": "https://api.github.com/repos/user/Repo1/keys{/key_id}",
                        "collaborators_url": "https://api.github.com/repos/user/Repo1/collaborators{/collaborator}",
                        "teams_url": "https://api.github.com/repos/user/Repo1/teams",
                        "hooks_url": "https://api.github.com/repos/user/Repo1/hooks",
                        "issue_events_url": "https://api.github.com/repos/user/Repo1/issues/events{/number}",
                        "events_url": "https://api.github.com/repos/user/Repo1/events",
                        "assignees_url": "https://api.github.com/repos/user/Repo1/assignees{/user}",
                        "branches_url": "https://api.github.com/repos/user/Repo1/branches{/branch}",
                        "tags_url": "https://api.github.com/repos/user/Repo1/tags",
                        "blobs_url": "https://api.github.com/repos/user/Repo1/git/blobs{/sha}",
                        "git_tags_url": "https://api.github.com/repos/user/Repo1/git/tags{/sha}",
                        "git_refs_url": "https://api.github.com/repos/user/Repo1/git/refs{/sha}",
                        "trees_url": "https://api.github.com/repos/user/Repo1/git/trees{/sha}",
                        "statuses_url": "https://api.github.com/repos/user/Repo1/statuses/{sha}",
                        "languages_url": "https://api.github.com/repos/user/Repo1/languages",
                        "stargazers_url": "https://api.github.com/repos/user/Repo1/stargazers",
                        "contributors_url": "https://api.github.com/repos/user/Repo1/contributors",
                        "subscribers_url": "https://api.github.com/repos/user/Repo1/subscribers",
                        "subscription_url": "https://api.github.com/repos/user/Repo1/subscription",
                        "commits_url": "https://api.github.com/repos/user/Repo1/commits{/sha}",
                        "git_commits_url": "https://api.github.com/repos/user/Repo1/git/commits{/sha}",
                        "comments_url": "https://api.github.com/repos/user/Repo1/comments{/comment_id}",
                        "issue_comment_url": "https://api.github.com/repos/user/Repo1/issues/comments{/number}",
                        "contents_url": "https://api.github.com/repos/user/Repo1/contents/{+path}",
                        "compare_url": "https://api.github.com/repos/user/Repo1/compare/{base}...{head}",
                        "merges_url": "https://api.github.com/repos/user/Repo1/merges",
                        "archive_url": "https://api.github.com/repos/user/Repo1/{archive_format}{/ref}",
                        "downloads_url": "https://api.github.com/repos/user/Repo1/downloads",
                        "issues_url": "https://api.github.com/repos/user/Repo1/issues{/number}",
                        "pulls_url": "https://api.github.com/repos/user/Repo1/pulls{/number}",
                        "milestones_url": "https://api.github.com/repos/user/Repo1/milestones{/number}",
                        "notifications_url": "https://api.github.com/repos/user/Repo1/notifications{?since,all,participating}",
                        "labels_url": "https://api.github.com/repos/user/Repo1/labels{/name}",
                        "releases_url": "https://api.github.com/repos/user/Repo1/releases{/id}",
                        "deployments_url": "https://api.github.com/repos/user/Repo1/deployments",
                        "created_at": "2023-01-01T00:00:00Z",
                        "updated_at": "2023-01-01T00:00:00Z",
                        "pushed_at": "2023-01-01T00:00:00Z",
                        "git_url": "git://github.com/user/Repo1.git",
                        "ssh_url": "git@github.com:user/Repo1.git",
                        "clone_url": "https://github.com/user/Repo1.git",
                        "svn_url": "https://github.com/user/Repo1",
                        "size": 100,
                        "stargazers_count": 10,
                        "watchers_count": 10,
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
                        "watchers": 10,
                        "default_branch": "main"
                    }
                ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        // 2. HttpClientFactory を使用してテスト用クライアントを作成
        val client = HttpClientFactory.create(mockEngine)
        val apiService = GitHubApiService(client)

        // 3. 実行
        val repos = apiService.listUserRepos("user")

        // 4. 内容をログに出力して「動いている実感」を得る
        logger.info("Fetched ${repos.size} repositories.")
        repos.forEach { repo ->
            logger.info("Repo: name=${repo.name}, stars=${repo.stargazersCount}, lang=${repo.language}, url=${repo.htmlUrl}")
            logger.info("  Owner: ${repo.owner.login}, Avatar: ${repo.owner.avatarUrl}")
            logger.info("  Created at: ${repo.createdAt}")
        }

        // 5. 検証
        assertEquals(1, repos.size)
        assertEquals("Repo1", repos[0].name)
        assertEquals(10, repos[0].stargazersCount)
        assertEquals("Kotlin", repos[0].language)
    }
}
