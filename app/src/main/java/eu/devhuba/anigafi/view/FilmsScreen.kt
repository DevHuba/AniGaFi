package eu.devhuba.anigafi.view

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

@Composable
fun FilmsScreen(navController: NavHostController, paddingValues: PaddingValues) {
	
	Column(
		modifier = Modifier.fillMaxSize()
				.padding(bottom = paddingValues.calculateBottomPadding())
				.background(color = Color.Black),
		horizontalAlignment = Alignment.CenterHorizontally,
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

@Composable
fun ShowFilmsList(navController: NavHostController) {
	val mockListOfFilms = listOf(
		"Dead or Alive",
		"Briljantovaja ruka",
		"Igra v kalmara",
		"Kurjer",
		"Mehanik",
		"Karlson",
		"Wtirlic"
	)
	
	LazyColumn {
		items(mockListOfFilms) { film ->
			Text(text = film, fontSize = 36.sp, color = Color.Yellow)
		}
	}
}


