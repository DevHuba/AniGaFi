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
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import eu.devhuba.anigafi.ui.theme.AniGaFiTheme
import eu.devhuba.anigafi.view.AnimeScreen
import eu.devhuba.anigafi.view.FilmsScreen

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
	@ExperimentalMaterialApi override fun onCreate(savedInstanceState: Bundle?) {
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

@OptIn(ExperimentalPagerApi::class) @ExperimentalMaterialApi @Composable fun AppScaffold(
	navController: NavHostController
) {
	
	val scaffoldState = rememberScaffoldState()
	val swappableState = rememberSwipeableState(0)
	val pagerState = rememberPagerState(initialPage = 0)
	val countOfDestinations = Destination.countObjects
	
	
	Scaffold(topBar = { }, bottomBar = { }, scaffoldState = scaffoldState
	) { paddingValues ->
		HorizontalPager(
			count = 3,
			state = pagerState,
			modifier = Modifier.fillMaxSize()
		) { page ->
			
			NavHost(
				navController = navController, startDestination = Destination.Anime.route,
			) {
				composable(Destination.Anime.route) {
					AnimeScreen(navController, paddingValues)
				}
				composable(Destination.Films.route) {
					FilmsScreen(navController, paddingValues)
				}
				
				
			}

//			when (page) {
//				0 -> {
//					Text(
//						text = "this is page 0 ",
//						color = Color.Red
//					)
//					Log.i("this", "page 0")
//				}
//				
//				1 -> {
//					Text(
//						text = "this is page 0 ",
//						color = Color.Red
//					)
//					Log.i("this", "page 1")
//				}
//				
//				2 -> {
//					Text(
//						text = "this is page 0 ",
//						color = Color.Red
//					)
//					Log.i("this", "page 2")
//				}
//			}
		}
//		NavHost(
//			navController = navController, startDestination = Destination.Anime.route,
//			
//			) {
//			composable(Destination.Anime.route) {
//				AnimeScreen(navController, paddingValues)
//			}
//			composable(Destination.Films.route) {
//				FilmsScreen(navController, paddingValues)
//			}
//		}
	}
}
