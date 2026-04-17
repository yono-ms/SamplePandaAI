package com.example.samplepandaai

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.samplepandaai.domain.usecase.IsSafeDomainUseCase
import com.example.samplepandaai.ui.features.RepoDetailScreen
import com.example.samplepandaai.ui.features.RepoListScreen
import com.example.samplepandaai.ui.features.UserNameHistoryScreen
import com.example.samplepandaai.ui.features.UserNameInputScreen
import com.example.samplepandaai.ui.features.license.LicenseDetailScreen
import com.example.samplepandaai.ui.features.license.LicenseScreen
import com.example.samplepandaai.ui.navigation.License
import com.example.samplepandaai.ui.navigation.LicenseDetail
import com.example.samplepandaai.ui.navigation.RepoDetail
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
    val isSafeDomainUseCase = IsSafeDomainUseCase()

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
                    },
                    onNavigateToLicense = {
                        navController.navigate(License)
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
                    username = repoListRoute.username,
                    onBack = {
                        navController.popBackStack()
                    },
                    onRepoClick = { repo ->
                        val encodedUrl = Uri.encode(repo.htmlUrl)
                        navController.navigate(RepoDetail(url = encodedUrl, title = repo.name))
                    }
                )
            }
            composable<RepoDetail> { backStackEntry ->
                val detail: RepoDetail = backStackEntry.toRoute()
                val decodedUrl = Uri.decode(detail.url)
                RepoDetailScreen(
                    url = decodedUrl,
                    title = detail.title,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    isSafeDomainUseCase = isSafeDomainUseCase
                )
            }
            composable<License> {
                LicenseScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onLicenseClick = { license ->
                        val encodedUrl = Uri.encode(license.url)
                        navController.navigate(
                            LicenseDetail(
                                url = encodedUrl,
                                title = license.name
                            )
                        )
                    }
                )
            }
            composable<LicenseDetail> { backStackEntry ->
                val detail: LicenseDetail = backStackEntry.toRoute()
                val decodedUrl = Uri.decode(detail.url)
                LicenseDetailScreen(
                    url = decodedUrl,
                    title = detail.title,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    isSafeDomainUseCase = isSafeDomainUseCase
                )
            }
        }
    }
}
