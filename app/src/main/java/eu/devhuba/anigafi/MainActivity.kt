@file:OptIn(ExperimentalFoundationApi::class)

package eu.devhuba.anigafi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.devhuba.anigafi.ui.theme.AniGaFiTheme
import eu.devhuba.anigafi.view.films.FilmsScreen
import eu.devhuba.anigafi.view.games.GamesScreen
import eu.devhuba.anigafi.view.anime.AnimeScreen

sealed class Destination(val route: String) {
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
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			AniGaFiTheme {
				// A surface container using the 'background' color from the theme
				Surface(
					modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
				) {
					val navController = rememberNavController()
					AppScaffold(navController = navController)
				}
			}
		}
	}
}


@ExperimentalMaterialApi
@Composable
fun AppScaffold(navController: NavHostController) {
	
	val scaffoldState = rememberScaffoldState()
	
	Scaffold(topBar = { }, bottomBar = { }, scaffoldState = scaffoldState
	) { paddingValues ->
		
		NavHost(
			navController = navController, startDestination = Destination.Anime.route
		) {
			composable(Destination.Anime.route) {
				val pagerState = rememberPagerState(initialPage = 1)
				
				HorizontalPager(state = pagerState, pageCount = 3) { page ->
					when (page) {
						0 -> {
							GamesScreen(navController, paddingValues)
						}
						
						1 -> {
							AnimeScreen(navController, paddingValues)
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
