package eu.devhuba.anigafi.view.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.ImageTemplate
import eu.devhuba.anigafi.model.AnimeApiResponse
import eu.devhuba.anigafi.model.Constants
import eu.devhuba.anigafi.model.api.NetworkResult
import eu.devhuba.anigafi.ui.theme.AppFontFamily
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
    result: NetworkResult<List<AnimeApiResponse>>,
    navController: NavHostController
) {

    val baseUrlForImage = Constants.BASE_URL_FOR_IMAGE

    result.data?.let { animes ->
        LazyColumn(
            modifier = Modifier.background(Color.Black),
            verticalArrangement = Arrangement.Top
        ) {
            items(animes) { anime ->
                val animeName = anime.anime?.name
                val imageUrl = baseUrlForImage + anime.anime?.image?.original

                Column(
                    modifier = Modifier

                        .background(Color.Black)
                        .fillMaxSize()
                        .wrapContentHeight()
                        .padding(16.dp, 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        ImageTemplate(
                            url = imageUrl,
                            modifier = Modifier
                                .weight(1f)
                        )
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .border(4.dp, color = Color.Red),
                            backgroundColor = Color.Green
                        ) {
                            Column() {
                                Text(text = "Title", fontFamily = AppFontFamily)
                                Text(text = "Description")
                                Text(text = "rating")
                            }

                        }
                    }

                }

            }

        }
    }
}
