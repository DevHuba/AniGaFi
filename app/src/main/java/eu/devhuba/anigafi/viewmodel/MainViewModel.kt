package eu.devhuba.anigafi.viewmodel

import android.app.Application
import android.util.Base64
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import eu.devhuba.anigafi.model.auth.AuthRepository
import timber.log.Timber
import java.security.SecureRandom

class MainViewMode(application: Application) : AndroidViewModel(application) {
	
	private var isAuthenticated by mutableStateOf(false)
	private var isFirstRun by mutableStateOf(true)
	
	private val authRepository = AuthRepository()
	
	init {

//        authServiceConfig = AuthorizationServiceConfiguration(
//            Uri.parse(ApiConstants.ANIME_AUTH_URI),  // authorization endpoint
//            Uri.parse(ApiConstants.ANIME_TOKEN_URI)
//        ) // token endpoint
		
		
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