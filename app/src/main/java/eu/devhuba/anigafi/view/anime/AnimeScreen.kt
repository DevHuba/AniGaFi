package eu.devhuba.anigafi.view.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.model.AnimeApiResponse
import eu.devhuba.anigafi.model.api.NetworkResult
import eu.devhuba.anigafi.viewmodel.AnimeApiViewModel

@Composable
fun AnimeScreen(
    navController: NavHostController,
    avm: AnimeApiViewModel,
    paddingValues: PaddingValues
) {

    val result by avm.result.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (result) {
                is NetworkResult.Initial -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Success -> {
                    ShowAnimeList(result, navController)
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}")
                }
            }
        }
    }

}

@Composable
fun ShowAnimeList(
    result: NetworkResult<AnimeApiResponse>,
    navController: NavHostController
) {
    result.data?.anime?.let { animes ->
        LazyColumn(
            modifier = Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.Top
        ) {
            items(animes) { anime ->
                val imageUrl = anime.image?.original
                val title = anime
            }
        }
    }
}

@Composable
fun ShowMockAnimeList() {
    val mockListOfAnime = listOf(
        "Demon Slayer",
        "Dorohedoro",
        "Naruto",
        "Baruto",
        "Shmaruto",
        "One Piece",
        "HunterVSHunter"
    )

    LazyColumn {
        items(mockListOfAnime) { anime ->
            Text(
                text = anime,
                fontSize = 36.sp,
                color = Color.Yellow
            )
        }
    }
}


