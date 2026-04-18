package com.example.samplepandaai.domain.model

import kotlin.time.Instant

/**
 * アプリケーション全体で扱うリポジトリのドメインモデル
 */
data class GitHubRepo(
    val id: Long,
    val name: String,
    val fullName: String,
    val description: String?,
    val htmlUrl: String,
    val stars: Int,
    val language: String?,
    val updatedAt: Instant
)
