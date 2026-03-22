package com.example.samplepandaai.domain.usecase

import com.example.samplepandaai.domain.model.GitHubRepo
import com.example.samplepandaai.domain.repository.GitHubRepository
import javax.inject.Inject

/**
 * 指定されたユーザーのリポジトリ一覧を取得する UseCase
 */
class GetGitHubReposUseCase @Inject constructor(
    private val repository: GitHubRepository
) {
    /**
     * リポジトリ一覧を取得し、必要に応じてビジネスルール（例：スター数順にソートなど）を適用する
     */
    suspend operator fun invoke(username: String): List<GitHubRepo> {
        return repository.getUserRepositories(username)
            .sortedByDescending { it.stars } // デフォルトでスター数が多い順にソート
    }
}
