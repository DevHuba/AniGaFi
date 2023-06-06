@file:OptIn(ExperimentalFoundationApi::class)

package eu.devhuba.anigafi


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.auth0.android.jwt.JWT
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import eu.devhuba.anigafi.ui.theme.AniGaFiTheme
import eu.devhuba.anigafi.ui.theme.DarkBlack
import eu.devhuba.anigafi.view.anime.AnimeScreen
import eu.devhuba.anigafi.view.auth.HomeScreen
import eu.devhuba.anigafi.view.films.FilmsScreen
import eu.devhuba.anigafi.view.games.GamesScreen
import eu.devhuba.anigafi.viewmodel.AnimeApiViewModel
import eu.devhuba.anigafi.viewmodel.MainViewModel
import net.openid.appauth.AuthState
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration

sealed class Destination(val route: String) {

    object Auth : Destination("auth")

    object Anime : Destination("anime")
    object AnimeDetail : Destination("character/{characterId}") {
        fun createRoute(animeId: Int?) = "anime/$animeId"
    }

    object Films : Destination("films")
    object FilmDetail : Destination("character/{characterId}") {
        fun createRoute(filmId: Int?) = "anime/$filmId"
    }

    object Games : Destination("games")
    object GameDetail : Destination("character/{characterId}") {
        fun createRoute(gameId: Int?) = "anime/$gameId"
    }
}


@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var authState: AuthState = AuthState()
    private var jwt: JWT? = null
    private lateinit var authorizationService: AuthorizationService
    lateinit var authServiceConfig: AuthorizationServiceConfiguration


    private val avm by viewModels<AnimeApiViewModel>()
    private val mvm by viewModels<MainViewModel>()

    override fun onNewIntent(intent: Intent?) {
        Log.i("this", "start -> start")
        super.onNewIntent(intent)
        val uri = intent?.data
        val code = uri?.lastPathSegment
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)











        setContent {
            AniGaFiTheme {
                //Style status bar
                val systemUiController = rememberSystemUiController()
                SideEffect {
                    systemUiController.setStatusBarColor(color = DarkBlack, darkIcons = false)
                }

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    AppScaffold(navController = navController, avm, mvm)
                }

            }
        }
    }
}


@ExperimentalMaterialApi
@Composable
fun AppScaffold(
    navController: NavHostController,
    avm: AnimeApiViewModel,
    mvm: MainViewModel
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(topBar = { }, bottomBar = { }, scaffoldState = scaffoldState
    ) { paddingValues ->

        NavHost(
            navController = navController, startDestination = Destination.Auth.route
        ) {

            composable(Destination.Auth.route) {
                HomeScreen(navController, paddingValues, mvm)
            }

            composable(Destination.Anime.route) {
                val pagerState = rememberPagerState(initialPage = 1)

                //		TODO: Check what screen was last

                HorizontalPager(state = pagerState, pageCount = 3) { page ->
                    when (page) {
                        0 -> {
                            GamesScreen(navController, paddingValues)
                        }

                        1 -> {
                            AnimeScreen(navController, avm, paddingValues)
                        }

                        2 -> {
                            FilmsScreen(navController, paddingValues)
                        }
                    }
                }

            }

        }

    }

}
