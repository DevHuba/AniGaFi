package eu.devhuba.anigafi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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

@OptIn(ExperimentalPagerApi::class)
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
				val pagerState = rememberPagerState()
				
				HorizontalPager(state = pagerState, count = 3) { page ->
					when (page) {
						0 -> {
							FilmsScreen(navController, paddingValues)
							Log.i("this", "$pageNumber")
							Log.i("this", "$pagerState")
						}
						
						1 -> {
							AnimeScreen(navController, paddingValues)
							Log.i("this", "$pagerState")
							
							// Add your second screen composable here
						}
						
						2 -> {
							FilmsScreen(navController, paddingValues)
							Log.i("this", "$pagerState")
							// Add your third screen composable here
						}
					}
				}
				
			}
			
		}
		
	}
	
}
