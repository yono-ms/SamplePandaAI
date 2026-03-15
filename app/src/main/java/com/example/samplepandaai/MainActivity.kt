package com.example.samplepandaai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.samplepandaai.data.remote.GitHubApiService
import com.example.samplepandaai.data.remote.HttpClientFactory
import com.example.samplepandaai.data.repository.GitHubRepositoryImpl
import com.example.samplepandaai.ui.features.RepoListScreen
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListViewModel
import io.ktor.client.engine.okhttp.OkHttp
import org.slf4j.LoggerFactory

class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.getLogger(MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("MainActivity onCreate called")

        // 手動での依存関係構築（暫定的なDI）
        val engine = OkHttp.create()
        val httpClient = HttpClientFactory.create(engine)
        val apiService = GitHubApiService(httpClient)
        val repository = GitHubRepositoryImpl(apiService)
        val viewModel = GitHubRepoListViewModel(repository)

        enableEdgeToEdge()
        setContent {
            SamplePandaAITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RepoListScreen(viewModel = viewModel)
                }
            }
        }
    }
}
