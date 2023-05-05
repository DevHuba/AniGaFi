package eu.devhuba.anigafi.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController

@Composable fun FilmsScreen(navController: NavHostController, paddingValues: PaddingValues) {
	
	Column(
		modifier = Modifier.fillMaxSize()
				.padding(bottom = paddingValues.calculateBottomPadding()),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Column(
			modifier = Modifier.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			ShowFilmsList(navController)
		}
	}
	
}

@Composable fun ShowFilmsList(navController: NavHostController) {
	val mockListOfAnime = listOf(
		"Demon Slayer", "Dorohedoro", "Naruto", "Baruto", "Shmaruto", "One Piece",
		"HunterVSHunter"
	)
	
	LazyColumn {
		items(mockListOfAnime) { anime ->
			Text(text = anime)
		}
	}
}


