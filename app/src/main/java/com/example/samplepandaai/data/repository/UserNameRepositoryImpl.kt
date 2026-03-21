package com.example.samplepandaai.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.samplepandaai.domain.repository.UserNameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserNameRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserNameRepository {

    private val historyKey = stringPreferencesKey("user_name_history")

    override fun getUserNameHistory(): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            val historyString = preferences[historyKey] ?: ""
            if (historyString.isEmpty()) emptyList() else historyString.split(",")
        }
    }

    override suspend fun addUserNameToHistory(userName: String) {
        dataStore.edit { preferences ->
            val currentHistory =
                preferences[historyKey]?.split(",")?.filter { it.isNotEmpty() }?.toMutableList()
                    ?: mutableListOf()

            // 重複を削除して先頭に追加（最新順ソート）
            currentHistory.remove(userName)
            currentHistory.add(0, userName)

            // 最大5件に制限
            val updatedHistory = currentHistory.take(5)
            preferences[historyKey] = updatedHistory.joinToString(",")
        }
    }

    override suspend fun deleteUserNameFromHistory(userName: String) {
        dataStore.edit { preferences ->
            val currentHistory =
                preferences[historyKey]?.split(",")?.filter { it.isNotEmpty() }?.toMutableList()
                    ?: mutableListOf()
            currentHistory.remove(userName)
            preferences[historyKey] = currentHistory.joinToString(",")
        }
    }
}
