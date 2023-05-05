package eu.devhuba.anigafi.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
	val mockListOfFilms = listOf(
		"Dead or Alive", "Briljantovaja ruka", "Igra v kalmara", "Kurjer", "Mehanik", "Karlson",
		"Wtirlic"
	)
	
	LazyColumn {
		items(mockListOfFilms) { film ->
			Text(text = film)
		}
	}
}


