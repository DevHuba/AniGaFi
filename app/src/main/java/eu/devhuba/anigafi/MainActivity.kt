package eu.devhuba.anigafi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.devhuba.anigafi.ui.theme.AniGaFiTheme
import eu.devhuba.anigafi.view.AnimeScreen
import eu.devhuba.anigafi.view.FilmsScreen

sealed class Destination(val route: String) {
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
					modifier = Modifier.fillMaxSize(),
					color = MaterialTheme.colors.background
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
	val swappableState = rememberSwipeableState(0)
	
	Scaffold(
		scaffoldState = scaffoldState,
		topBar = { },
		bottomBar = { }
	) { paddingValues ->
		NavHost(navController = navController, startDestination = Destination.Anime.route) {
			composable(Destination.Anime.route) {
				AnimeScreen(navController, paddingValues)
			}
			composable(Destination.Films.route) {
				FilmsScreen(navController, paddingValues)
			}
		}
	}
}