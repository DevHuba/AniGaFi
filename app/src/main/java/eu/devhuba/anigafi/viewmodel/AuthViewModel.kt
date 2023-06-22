package eu.devhuba.anigafi.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Base64
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import eu.devhuba.anigafi.R
import eu.devhuba.anigafi.model.auth.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import timber.log.Timber
import java.security.SecureRandom

class AuthViewMode(application: Application) : AndroidViewModel(application) {
	
	private var isAuthenticated by mutableStateOf(false)
	private var isFirstRun by mutableStateOf(true)
	
	private val authRepository = AuthRepository()
	private val authService: AuthorizationService = AuthorizationService(getApplication())
	
	private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
	private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
	private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)
	
	private val loadingMutableStateFlow = MutableStateFlow(false)
	
	val openAuthPageFlow: Flow<Intent>
		get() = openAuthPageEventChannel.receiveAsFlow()
	
	val loadingFlow: Flow<Boolean>
		get() = loadingMutableStateFlow.asStateFlow()
	
	val toastFlow: Flow<Int>
		get() = toastEventChannel.receiveAsFlow()
	
	val authSuccessFlow: Flow<Unit>
		get() = authSuccessEventChannel.receiveAsFlow()
	
	fun onAuthCodeFailed(exception: AuthorizationException) {
		toastEventChannel.trySendBlocking(R.string.authorization_canceled)
	}
	
	fun onAuthCodeReceived(tokenRequest: TokenRequest) {
		
		Timber.tag("oauth").d("3. Received code = ${tokenRequest.authorizationCode}")
		
		viewModelScope.launch {
			loadingMutableStateFlow.value = true
			
			runCatching {
				Timber.tag("oauth")
						.d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
				authRepository.performTokenRequest(
					authService = authService,
					tokenRequest = tokenRequest
				)
			}.onSuccess {
				loadingMutableStateFlow.value = false
				authSuccessEventChannel.send(Unit)
			}.onFailure {
				loadingMutableStateFlow.value = true
				toastEventChannel.send(R.string.auth_doesnt_completed)
				
			}
		}
	}
	
	fun openLoginPage() {
		val customTabsIntent = CustomTabsIntent.Builder().build()
		val authRequest = authRepository.getAuthRequest()
		
		Timber.tag("oauth")
				.d("1. Generate verifier = ${authRequest.codeVerifier}, challenge = ${authRequest.codeVerifierChallenge}")
		
		val openAuthPageIntent = authService.getAuthorizationRequestIntent(
			authRequest,
			customTabsIntent
		)
		
		val result = openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
		
		if (result.isFailure) {
			Timber.tag("oauth")
					.e(
						result.exceptionOrNull(),
						"Failed to send intent to openAuthPageEventChannel"
					)
		} else {
			Timber.tag("oauth").d("result -> $result")
		}
		
		Timber.tag("oauth").d("2. Open auth page: ${authRequest.toUri()}")
		
	}
	
	override fun onCleared() {
		super.onCleared()
		authService.dispose()
	}
	
	
	fun login() {
		
		
		Timber.d("login")
		
		val secureRandom = SecureRandom()
		val bytes = ByteArray(64)
		secureRandom.nextBytes(bytes)
		
		val encoding = Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP
		val codeVerifier = Base64.encodeToString(bytes, encoding)

//		val digest = MessageDigest.getInstance(ApiConstants.MESSAGE_DIGEST_ALGORITHM)
//		val hash = digest.digest(codeVerifier.toByteArray())
//		val codeChallenge = Base64.encodeToString(hash, encoding)

//		val builder = AuthorizationRequest.Builder(
//			authServiceConfig,
//			ApiConstants.ANIME_CLIENT_ID,
//			
//			//TODO: maybe this is the error place check redirect url
//			
//			ApiConstants.ANIME_RESPONSE_TYPE, Uri.parse(ApiConstants.URL_AUTH_REDIRECT)
//		)

//		Timber.d("builder -> $builder")
		
		
		isAuthenticated = true
		isFirstRun = false
		
	}
	
	fun logOut() {
		isAuthenticated = false
	}
	
}
