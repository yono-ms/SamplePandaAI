package com.example.samplepandaai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.samplepandaai.ui.features.RepoListScreen
import com.example.samplepandaai.ui.features.UserNameHistoryScreen
import com.example.samplepandaai.ui.features.UserNameInputScreen
import com.example.samplepandaai.ui.navigation.RepoList
import com.example.samplepandaai.ui.navigation.UserNameHistory
import com.example.samplepandaai.ui.navigation.UserNameInput
import com.example.samplepandaai.ui.theme.SamplePandaAITheme
import com.example.samplepandaai.ui.viewmodel.GitHubRepoListViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.slf4j.LoggerFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val logger = LoggerFactory.getLogger(MainActivity::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logger.debug("MainActivity onCreate with Type-safe Navigation")

        enableEdgeToEdge()
        setContent {
            SamplePandaAITheme {
                SamplePandaApp()
            }
        }
    }
}

@Composable
fun SamplePandaApp() {
    val navController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = UserNameInput
        ) {
            composable<UserNameInput> {
                UserNameInputScreen(
                    onNavigateToRepoList = { username ->
                        navController.navigate(RepoList(username))
                    },
                    onNavigateToHistory = {
                        navController.navigate(UserNameHistory)
                    }
                )
            }
            composable<UserNameHistory> {
                UserNameHistoryScreen(
                    onNavigateToRepoList = { username ->
                        navController.navigate(RepoList(username))
                    },
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
            composable<RepoList> { backStackEntry ->
                val repoListRoute: RepoList = backStackEntry.toRoute()
                val viewModel: GitHubRepoListViewModel = hiltViewModel()
                RepoListScreen(
                    viewModel = viewModel,
                    username = repoListRoute.username
                )
            }
        }
    }
}
