package com.example.samplepandaai.ui.navigation

import kotlinx.serialization.Serializable

/**
 * ユーザー名入力画面
 */
@Serializable
object UserNameInput

/**
 * 検索履歴画面
 */
@Serializable
object UserNameHistory

/**
 * リポジトリ一覧画面
 * @param username 取得対象の GitHub ユーザー名
 */
@Serializable
data class RepoList(val username: String)
