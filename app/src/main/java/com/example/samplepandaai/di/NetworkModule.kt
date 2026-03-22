package com.example.samplepandaai.di

import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.remote.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.engine.okhttp.OkHttp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideGitHubApiService(): GitHubApiService {
        // 通常の OkHttp エンジンを使用する HttpClient を提供
        val engine = OkHttp.create()
        val httpClient = HttpClientFactory.create(engine)
        return GitHubApiService(httpClient)
    }
}
