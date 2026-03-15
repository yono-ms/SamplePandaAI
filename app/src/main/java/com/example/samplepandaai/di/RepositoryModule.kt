package com.example.samplepandaai.di

import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.repository.GitHubRepositoryImpl
import com.example.samplepandaai.domain.repository.GitHubRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
}
