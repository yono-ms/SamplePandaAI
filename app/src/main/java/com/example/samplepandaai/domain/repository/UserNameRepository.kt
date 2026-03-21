package com.example.samplepandaai.domain.repository

import kotlinx.coroutines.flow.Flow

/**
 * ユーザー名の履歴を管理するリポジトリのインターフェース。
 */
interface UserNameRepository {
    /**
     * 保存されているユーザー名の履歴を、新しい順（最後に使用された順）で取得する。
     */
    fun getUserNameHistory(): Flow<List<String>>

    /**
     * ユーザー名を履歴に追加する。
     * すでに存在する場合は順序を最新にし、最大5件を超えた場合は古いものを削除する。
     */
    suspend fun addUserNameToHistory(userName: String)

    /**
     * 指定されたユーザー名を履歴から削除する。
     */
    suspend fun deleteUserNameFromHistory(userName: String)
}
