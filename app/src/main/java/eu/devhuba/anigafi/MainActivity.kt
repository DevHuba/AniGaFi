@file:OptIn(ExperimentalFoundationApi::class)

package eu.devhuba.anigafi

import android.os.Bundle
import android.util.Log
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
import eu.devhuba.anigafi.view.AnimeScreen
import eu.devhuba.anigafi.view.FilmsScreen
import eu.devhuba.anigafi.view.GamesScreen

sealed class Destination(val route: String) {
	
	//Count our screens
	companion object {
		var countObjects = 0
	}
	
	init {
		countObjects++
	}
	
	object Anime : Destination("anime")
	object Films : Destination("films")
	object Games : Destination("games")

//    object CharacterDetail : Destination("character/{characterId}") {
//        fun createRoute(characterId: Int?) = "character/$characterId"
//    }
}

class MainActivity : ComponentActivity() {
	@ExperimentalMaterialApi
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
	val pageNumber = Destination.countObjects
	
	Scaffold(topBar = { }, bottomBar = { }, scaffoldState = scaffoldState
	) { paddingValues ->
		
		NavHost(
			navController = navController, startDestination = Destination.Anime.route
		) {
			composable(Destination.Anime.route) {
				val pagerState = rememberPagerState(initialPage = 1)
				
				HorizontalPager(state = pagerState, pageCount = 3, ) { page ->
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
