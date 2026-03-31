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

/**
 * リポジトリ詳細画面
 * @param url 表示対象の GitHub リポジトリ URL (html_url)
 * @param title 画面上部に表示するリポジトリ名
 */
@Serializable
data class RepoDetail(val url: String, val title: String)

/**
 * ライセンス情報画面
 */
@Serializable
object License
