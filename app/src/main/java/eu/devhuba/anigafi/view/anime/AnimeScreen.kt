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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.ImageTemplate
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.maxLengthName
import eu.devhuba.anigafi.model.AnimeApiResponse
import eu.devhuba.anigafi.model.Constants
import eu.devhuba.anigafi.model.api.NetworkResult
import eu.devhuba.anigafi.ui.theme.Beige
import eu.devhuba.anigafi.ui.theme.Green
import eu.devhuba.anigafi.ui.theme.Orange
import eu.devhuba.anigafi.ui.theme.Typography
import eu.devhuba.anigafi.viewmodel.AnimeApiViewModel
import java.time.OffsetDateTime

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
					ShowAnimeList(
						result, navController
					)
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
	navController: NavHostController,
) {
	val baseUrlForImage = Constants.BASE_URL_FOR_IMAGE
	
	result.data?.let { animes ->
		LazyColumn(
			modifier = Modifier.background(Color.Black), verticalArrangement = Arrangement.Top
		
		) {
			items(animes) { anime ->
				
				val textAnimeNameRUS = anime.anime?.russian
				val imageUrl = baseUrlForImage + anime.anime?.image?.original
				val formattedRateNumber = anime.anime?.score?.dropLast(1)?.toFloat()
				var iconRate = painterResource(id = R.drawable.ic_low_rate)
				var iconRateColor = Green
				var textRateColor = Color.White
				val textNextEpisode = anime.next_episode_at
				val formattedNextEpisodeDate = OffsetDateTime.parse(textNextEpisode)
				val textEpisodeNumber = anime.next_episode
				
				val type = if (anime.anime?.kind == "tv") "Сериал" else "ХЗ ЧТО ЭТО"
				val formattedAnimeName = if (textAnimeNameRUS?.length!! > maxLengthName) {
					textAnimeNameRUS.take(maxLengthName).plus("...")
				} else textAnimeNameRUS
				
				//Icons
				if (formattedRateNumber != null) {
					iconRate = when {
						formattedRateNumber > 8.0 -> {
							iconRateColor = Color.Yellow
							textRateColor = Color.Black
							painterResource(id = R.drawable.ic_high_rate)
						}
						
						formattedRateNumber in 5.0..8.0 -> {
							iconRateColor = Green
							textRateColor = Beige
							painterResource(id = R.drawable.ic_high_rate)
						}
						
						else -> {
							iconRateColor = Color.Black
							textRateColor = Color.White
							painterResource(id = R.drawable.ic_high_rate)
						}
					}
				}
				
				Column(
					modifier = Modifier
							.background(Color.Black)
							.fillMaxSize()
							.wrapContentHeight()
							.padding(16.dp, 4.dp)
				
				) {
					Row(
						modifier = Modifier.fillMaxWidth()
					) {
						Box(modifier = Modifier.weight(1f)) {
							ImageTemplate(
								url = imageUrl, modifier = Modifier.fillMaxWidth()
							)
							
							Box(
//								modifier = Modifier.align(Alignment.Top)
							) {
								Icon(
									modifier = Modifier.size(70.dp),
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
							
						}
						
						Card(
							modifier = Modifier.weight(1f),
							backgroundColor = Color.Black,
						) {
							Column(
								modifier = Modifier.padding(16.dp)
							) {
								Text(
									text = formattedAnimeName,
									modifier = Modifier.padding(0.dp, 4.dp),
									style = Typography.h1
								)
								Text(text = "Тип : $type", modifier = Modifier.padding(0.dp, 4.dp))
								Text(
									text = "Следующий эпизод : ",
									modifier = Modifier.padding(0.dp, 4.dp)
								)
								Text(
									text = "${formattedNextEpisodeDate.dayOfMonth} " + "${formattedNextEpisodeDate.month} " + "${formattedNextEpisodeDate.year} " + "${formattedNextEpisodeDate.hour}" + ":" + "${formattedNextEpisodeDate.minute}",
									color = Orange,
								)
								Text(
									text = "Номер серии : $textEpisodeNumber",
									modifier = Modifier.padding(0.dp, 4.dp)
								)
							}
						}

//						Box(
//							modifier = Modifier.align(Alignment.Top)
//						) {
//							Icon(
//								modifier = Modifier.size(70.dp),
//								painter = iconRate,
//								contentDescription = stringResource(R.string.rate_icon),
//								tint = iconRateColor
//							)
//							
//							Text(
//								color = textRateColor,
//								modifier = Modifier.align(Alignment.Center),
//								text = "$formattedRateNumber",
//								style = Typography.h3
//							)
//						}
					}
				}
			}
		}
	}
}
