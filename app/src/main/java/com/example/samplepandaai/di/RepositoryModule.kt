package com.example.samplepandaai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.repository.GitHubRepositoryImpl
import com.example.samplepandaai.data.repository.UserNameRepositoryImpl
import com.example.samplepandaai.domain.repository.GitHubRepository
import com.example.samplepandaai.domain.repository.UserNameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideGitHubRepository(
        apiService: GitHubApiService
    ): GitHubRepository {
        return GitHubRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideUserNameRepository(
        dataStore: DataStore<Preferences>
    ): UserNameRepository {
        return UserNameRepositoryImpl(dataStore)
    }
}
