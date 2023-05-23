package eu.devhuba.anigafi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

const val maxLengthName = 60

@Composable
fun ImageTemplate(
	url: String?,
	modifier: Modifier,
	contentScale: ContentScale = ContentScale.FillWidth
) {
	AsyncImage(
		model = url, contentDescription = null, modifier = modifier, contentScale = contentScale
	)
}

@Composable
fun choseRateIcon(rate: Int): Painter {
	return when {
		rate < 5 -> painterResource(id = R.drawable.ic_low_rate)
		rate in 5..6 -> painterResource(id = R.drawable.ic_middle_rate)
		else -> painterResource(id = R.drawable.ic_high_rate)
	}
}
