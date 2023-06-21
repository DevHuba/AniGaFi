package eu.devhuba.anigafi.view.auth

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.ui.theme.DarkBlack
import eu.devhuba.anigafi.viewmodel.AuthViewMode
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import timber.log.Timber

@Composable
fun HomeScreen(
	navController: NavHostController,
	paddingValues: PaddingValues,
	auvm: AuthViewMode
) {
	
	fun handleAuthResponseIntent(intent: Intent) {
		// пытаемся получить ошибку из ответа. null - если все ок
		val exception = AuthorizationException.fromIntent(intent)
		// пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
		val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
				?.createTokenExchangeRequest()
		when {
			// авторизация завершались ошибкой
			exception != null -> auvm.onAuthCodeFailed(exception)
			// авторизация прошла успешно, меняем код на токен
			tokenExchangeRequest != null ->
				auvm.onAuthCodeReceived(tokenExchangeRequest)
		}
		
	}
	
	val getAuthResponse = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.StartActivityForResult(),
		onResult = {
			
			if (it.data != null) Timber.tag("oauth").d("{it.data} -> ${it.data}")
			
			val dataIntent = it.data ?: return@rememberLauncherForActivityResult
			handleAuthResponseIntent(dataIntent)
		}
	)
	
	fun openAuthPage(intent: Intent) {
		getAuthResponse.launch(intent)
	}
	
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
				
				auvm.openLoginPage()
				
			}) {
			Text(text = "Login for Anime data")
		}
	}
	
}
