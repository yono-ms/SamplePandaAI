package com.example.samplepandaai.di

import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.remote.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        val mockEngine = MockEngine { _ ->
            respond(
                content = """
                    [
                      {
                        "id": 12345,
                        "node_id": "MDEwOlJlcG9zaXRvcnkxMjM0NQ==",
                        "name": "sample-repo",
                        "full_name": "owner/sample-repo",
                        "private": false,
                        "owner": {
                          "login": "owner",
                          "id": 1,
                          "node_id": "MDQ6VXNlcjE=",
                          "avatar_url": "https://github.com/images/error/octocat_happy.gif",
                          "gravatar_id": "",
                          "url": "https://api.github.com/users/owner",
                          "html_url": "https://github.com/owner",
                          "followers_url": "https://api.github.com/users/owner/followers",
                          "following_url": "https://api.github.com/users/owner/following",
                          "gists_url": "https://api.github.com/users/owner/gists",
                          "starred_url": "https://api.github.com/users/owner/starred",
                          "subscriptions_url": "https://api.github.com/users/owner/subscriptions",
                          "organizations_url": "https://api.github.com/users/owner/orgs",
                          "repos_url": "https://api.github.com/users/owner/repos",
                          "events_url": "https://api.github.com/users/owner/events",
                          "received_events_url": "https://api.github.com/users/owner/received_events",
                          "type": "User",
                          "site_admin": false
                        },
                        "html_url": "https://github.com/owner/sample-repo",
                        "description": "This is a sample repository",
                        "fork": false,
                        "url": "https://api.github.com/repos/owner/sample-repo",
                        "forks_url": "https://api.github.com/repos/owner/sample-repo/forks",
                        "keys_url": "https://api.github.com/repos/owner/sample-repo/keys",
                        "collaborators_url": "https://api.github.com/repos/owner/sample-repo/collaborators",
                        "teams_url": "https://api.github.com/repos/owner/sample-repo/teams",
                        "hooks_url": "https://api.github.com/repos/owner/sample-repo/hooks",
                        "issue_events_url": "https://api.github.com/repos/owner/sample-repo/issues/events",
                        "events_url": "https://api.github.com/repos/owner/sample-repo/events",
                        "assignees_url": "https://api.github.com/repos/owner/sample-repo/assignees",
                        "branches_url": "https://api.github.com/repos/owner/sample-repo/branches",
                        "tags_url": "https://api.github.com/repos/owner/sample-repo/tags",
                        "blobs_url": "https://api.github.com/repos/owner/sample-repo/git/blobs",
                        "git_tags_url": "https://api.github.com/repos/owner/sample-repo/git/tags",
                        "git_refs_url": "https://api.github.com/repos/owner/sample-repo/git/refs",
                        "trees_url": "https://api.github.com/repos/owner/sample-repo/git/trees",
                        "statuses_url": "https://api.github.com/repos/owner/sample-repo/statuses",
                        "languages_url": "https://api.github.com/repos/owner/sample-repo/languages",
                        "stargazers_url": "https://api.github.com/repos/owner/sample-repo/stargazers",
                        "contributors_url": "https://api.github.com/repos/owner/sample-repo/contributors",
                        "subscribers_url": "https://api.github.com/repos/owner/sample-repo/subscribers",
                        "subscription_url": "https://api.github.com/repos/owner/sample-repo/subscription",
                        "commits_url": "https://api.github.com/repos/owner/sample-repo/commits",
                        "git_commits_url": "https://api.github.com/repos/owner/sample-repo/git/commits",
                        "comments_url": "https://api.github.com/repos/owner/sample-repo/comments",
                        "issue_comment_url": "https://api.github.com/repos/owner/sample-repo/issues/comments",
                        "contents_url": "https://api.github.com/repos/owner/sample-repo/contents",
                        "compare_url": "https://api.github.com/repos/owner/sample-repo/compare",
                        "merges_url": "https://api.github.com/repos/owner/sample-repo/merges",
                        "archive_url": "https://api.github.com/repos/owner/sample-repo/archive",
                        "downloads_url": "https://api.github.com/repos/owner/sample-repo/downloads",
                        "issues_url": "https://api.github.com/repos/owner/sample-repo/issues",
                        "pulls_url": "https://api.github.com/repos/owner/sample-repo/pulls",
                        "milestones_url": "https://api.github.com/repos/owner/sample-repo/milestones",
                        "notifications_url": "https://api.github.com/repos/owner/sample-repo/notifications",
                        "labels_url": "https://api.github.com/repos/owner/sample-repo/labels",
                        "releases_url": "https://api.github.com/repos/owner/sample-repo/releases",
                        "deployments_url": "https://api.github.com/repos/owner/sample-repo/deployments",
                        "created_at": "2024-01-01T00:00:00Z",
                        "updated_at": "2024-01-02T10:00:00Z",
                        "pushed_at": "2024-01-03T20:00:00Z",
                        "git_url": "git://github.com/owner/sample-repo.git",
                        "ssh_url": "git@github.com:owner/sample-repo.git",
                        "clone_url": "https://github.com/owner/sample-repo.git",
                        "svn_url": "https://github.com/owner/sample-repo",
                        "size": 1024,
                        "stargazers_count": 99,
                        "watchers_count": 99,
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
                        "watchers": 99,
                        "default_branch": "main"
                      }
                    ]
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val httpClient = HttpClientFactory.create(mockEngine)
        return GitHubApiService(httpClient)
    }
}
