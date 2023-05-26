package eu.devhuba.anigafi.view.auth

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.model.ApiConstants
import eu.devhuba.anigafi.ui.theme.DarkBlack

@Composable
fun HomeScreen(
	navController: NavHostController,
	paddingValues: PaddingValues
) {
	val context = LocalContext.current
	
	Column(
		modifier = Modifier
				.fillMaxSize()
				.background(DarkBlack),
	) {
		
		Image(
			painter = painterResource(id = R.drawable.logo),
			modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
			contentDescription = null
		)
		
		Button(
			modifier = Modifier.align(Alignment.CenterHorizontally),
			onClick = {
				val url = ApiConstants.ANIME_AUTH_URI
				val customTabsIntent = CustomTabsIntent.Builder().build()
				customTabsIntent.launchUrl(context, Uri.parse(url))
			}) {
			Text(text = "Login in Shikimori")
		}
	}
	
}