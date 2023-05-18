package eu.devhuba.anigafi.view.anime

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

@Composable
fun AnimeScreen(navController: NavHostController, paddingValues: PaddingValues) {

	
	
	Column(
		modifier = Modifier.fillMaxSize()
				.padding(bottom = paddingValues.calculateBottomPadding())
				.background(Color.Black),
		horizontalAlignment = Alignment.CenterHorizontally,
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			ShowAnimeList(navController)
		}
	}
	
}

@Composable
fun ShowAnimeList(navController: NavHostController) {
	val mockListOfAnime = listOf(
		"Demon Slayer", "Dorohedoro", "Naruto", "Baruto", "Shmaruto", "One Piece", "HunterVSHunter"
	)
	
	LazyColumn {
		items(mockListOfAnime) { anime ->
			Text(text = anime, fontSize = 36.sp, color = Color.Yellow)
		}
	}
}


