@file:Suppress("NonAsciiCharacters")

package com.example.samplepandaai.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.example.samplepandaai.domain.repository.UserNameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class UserNameRepositoryImplTest {

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var repository: UserNameRepository
    private lateinit var testDataStore: DataStore<Preferences>

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        val testFile = tmpFolder.newFile("test_user_preferences.preferences_pb")
        testDataStore = PreferenceDataStoreFactory.create(
            scope = testScope,
            produceFile = { testFile }
        )

        // 【重要】本番の実装クラス (UserNameRepositoryImpl) を直接テスト対象とする
        repository = UserNameRepositoryImpl(testDataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUserNameHistory - 初期状態では空リストを返すこと`() = runTest {
        val history = repository.getUserNameHistory().first()
        assertEquals(0, history.size)
    }

    @Test
    fun `addUserNameToHistory - 名前を追加すると先頭に追加されること`() = runTest {
        repository.addUserNameToHistory("user1")
        repository.addUserNameToHistory("user2")

        val history = repository.getUserNameHistory().first()
        assertEquals(listOf("user2", "user1"), history)
    }

    @Test
    fun `addUserNameToHistory - 重複する名前を追加すると先頭に移動すること`() = runTest {
        repository.addUserNameToHistory("user1")
        repository.addUserNameToHistory("user2")
        repository.addUserNameToHistory("user1")

        val history = repository.getUserNameHistory().first()
        assertEquals(listOf("user1", "user2"), history)
    }

    @Test
    fun `addUserNameToHistory - 5件を超えると古いものが削除されること`() = runTest {
        repository.addUserNameToHistory("user1")
        repository.addUserNameToHistory("user2")
        repository.addUserNameToHistory("user3")
        repository.addUserNameToHistory("user4")
        repository.addUserNameToHistory("user5")
        repository.addUserNameToHistory("user6")

        val history = repository.getUserNameHistory().first()
        assertEquals(listOf("user6", "user5", "user4", "user3", "user2"), history)
    }

    @Test
    fun `deleteUserNameFromHistory - 指定した名前が削除されること`() = runTest {
        repository.addUserNameToHistory("user1")
        repository.addUserNameToHistory("user2")

        repository.deleteUserNameFromHistory("user1")

        val history = repository.getUserNameHistory().first()
        assertEquals(listOf("user2"), history)
    }
}
