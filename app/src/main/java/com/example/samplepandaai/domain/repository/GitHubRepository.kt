package com.example.samplepandaai.domain.repository

import com.example.samplepandaai.domain.model.GitHubRepo

/**
 * GitHub API とのデータ通信を抽象化するインターフェース
 */
interface GitHubRepository {
    /**
     * 指定したユーザーの公開リポジトリ一覧を取得する
     *
     * @param username GitHub ユーザー名
     * @return リポジトリのリスト
     */
    suspend fun getUserRepositories(username: String): List<GitHubRepo>
}
