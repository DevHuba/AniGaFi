package eu.devhuba.anigafi.view.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.AppConstants
import eu.devhuba.anigafi.ImageTemplate
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.model.AnimeApiResponse
import eu.devhuba.anigafi.model.ApiConstants
import eu.devhuba.anigafi.model.api.NetworkResult
import eu.devhuba.anigafi.ui.theme.Beige
import eu.devhuba.anigafi.ui.theme.Green
import eu.devhuba.anigafi.ui.theme.Orange
import eu.devhuba.anigafi.ui.theme.Orange2
import eu.devhuba.anigafi.ui.theme.Typography
import eu.devhuba.anigafi.viewmodel.AnimeApiViewModel
import eu.wewox.textflow.TextFlow
import eu.wewox.textflow.TextFlowObstacleAlignment
import java.time.OffsetDateTime

@Composable
fun AnimeScreen(
    navController: NavHostController,
    avm: AnimeApiViewModel,
    paddingValues: PaddingValues
) {

    val result by avm.result.collectAsState()
    var textSearch by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValues.calculateBottomPadding())
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        //Search bar
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 8.dp, 8.dp),
            value = textSearch,
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search), contentDescription = null
                )
            },
            onValueChange = { textSearch = it },
            label = { Text(text = stringResource(R.string.anime_search_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = {
                //TODO: here need to be search request	
                textSearch = ""
            }),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Orange2,
                focusedBorderColor = Orange,
                focusedLabelColor = Orange
            )
        )

        //Content list
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
                    ShowAnimeList(
                        result, navController
                    )
                }

                is NetworkResult.Loading -> {
                    CircularProgressIndicator()
                }

                is NetworkResult.Error -> {
                    Text(text = "Error: ${result.message}", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun ShowAnimeList(
    result: NetworkResult<List<AnimeApiResponse>>,
    navController: NavHostController,
) {
    val baseUrlForImage = ApiConstants.ANIME_BASE_URL_FOR_IMAGE

    result.data?.let { animes ->
        LazyColumn(
            modifier = Modifier.background(Color.Black), verticalArrangement = Arrangement.Top

        ) {
            items(animes) { anime ->

                val textAnimeNameRUS = anime.anime?.russian
                val imageUrl = baseUrlForImage + anime.anime?.image?.original
                val formattedRateNumber = anime.anime?.score?.dropLast(1)
                    ?.toFloat()
                var iconRate = painterResource(id = R.drawable.ic_rate_star)
                var iconRateColor = Green
                var textRateColor = Color.White
                val textNextEpisodeDate = anime.next_episode_at
                val formattedNextEpisodeDate = OffsetDateTime.parse(textNextEpisodeDate)
                val textNextEpisodeTitle = stringResource(R.string.title_next_episode)
                val textNextEpisodeNumber = anime.next_episode

                val type = if (anime.anime?.kind == "tv") "Сериал" else "Неизвестный"
                val formattedAnimeName =
                    if (textAnimeNameRUS?.length!! > AppConstants.ANIME_NAME_MAX_LENGTH) {
                        textAnimeNameRUS.take(AppConstants.ANIME_NAME_MAX_LENGTH)
                            .plus("...")
                    } else textAnimeNameRUS

                //Style inside String
                val annotatedTextNextEpisode = buildAnnotatedString {
                    append(textNextEpisodeTitle)
                    withStyle(style = SpanStyle(color = Orange)) {
                        append(textNextEpisodeNumber)
                    }
                }

                //Icons
                if (formattedRateNumber != null) {
                    iconRate = when {
                        formattedRateNumber > 7.2 -> {
                            iconRateColor = Color.Yellow
                            textRateColor = Color.Black
                            painterResource(id = R.drawable.ic_rate_star)
                        }

                        formattedRateNumber in 5.0..7.2 -> {
                            iconRateColor = Green
                            textRateColor = Beige
                            painterResource(id = R.drawable.ic_rate_star)
                        }

                        else -> {
                            iconRateColor = Beige
                            textRateColor = Color.Black
                            painterResource(id = R.drawable.ic_rate_star)
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                        .wrapContentHeight()
                        .padding(8.dp, 4.dp)

                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ImageTemplate(
                                url = imageUrl, modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Card(
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color.Black,
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                TextFlow(text = formattedAnimeName,
                                    modifier = Modifier,
                                    style = Typography.h1,
                                    obstacleAlignment = TextFlowObstacleAlignment.TopEnd,
                                    obstacleContent = {
                                        Box(
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(50.dp),
                                                painter = iconRate,
                                                contentDescription = stringResource(R.string.rate_icon),
                                                tint = iconRateColor
                                            )

                                            Text(
                                                text = "$formattedRateNumber",
                                                color = textRateColor,
                                                modifier = Modifier.align(Alignment.Center),
                                                style = Typography.h3
                                            )
                                        }
                                    })
                                Text(text = "Тип : $type", modifier = Modifier.padding(0.dp, 4.dp))
                                Text(
                                    text = stringResource(R.string.anime_text_next_episode),
                                    modifier = Modifier.padding(0.dp, 4.dp)
                                )
                                Text(
                                    text = "${formattedNextEpisodeDate.dayOfMonth} " + "${formattedNextEpisodeDate.month} " + "${formattedNextEpisodeDate.hour}" + ":" + "${formattedNextEpisodeDate.minute}",
                                    color = Orange,
                                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 4.dp)
                                )
                                Text(
                                    text = annotatedTextNextEpisode,
                                    modifier = Modifier.padding(0.dp, 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
