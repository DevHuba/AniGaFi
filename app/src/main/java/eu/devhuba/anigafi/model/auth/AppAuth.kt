package eu.devhuba.anigafi.model.auth

import android.net.Uri
import androidx.core.net.toUri
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ClientAuthentication
import net.openid.appauth.ClientSecretPost
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.GrantTypeValues
import net.openid.appauth.ResponseTypeValues
import net.openid.appauth.TokenRequest
import timber.log.Timber


object AppAuth {
	
	private val serviceConfiguration = AuthorizationServiceConfiguration(
		Uri.parse(AuthConfig.ANIME_AUTH_URI),
		Uri.parse(AuthConfig.ANIME_TOKEN_URI),
		null,
		Uri.parse(AuthConfig.ANIME_END_SESSION_URI)
	)
	
	fun getAuthRequest(): AuthorizationRequest {
		val redirectUri = AuthConfig.ANIME_CALLBACK_URL.toUri()
		
		return AuthorizationRequest.Builder(
			serviceConfiguration,
			AuthConfig.ANIME_CLIENT_ID,
			AuthConfig.ANIME_RESPONSE_TYPE,
			redirectUri
		).setScope(AuthConfig.ANIME_SCOPE).build()
		
	}
	
	fun getEndSessionRequest(): EndSessionRequest {
		return EndSessionRequest.Builder(serviceConfiguration)
				.setPostLogoutRedirectUri(AuthConfig.ANIME_LOGOUT_CALLBACK_URL.toUri())
				.build()
	}
	
	fun getRefreshToken(refreshToken: String): TokenRequest {
		
		Timber.tag("oauth").d("refreshToken -> $refreshToken")
		
		return TokenRequest.Builder(
			serviceConfiguration,
			AuthConfig.ANIME_CLIENT_ID
		)
				.setGrantType(GrantTypeValues.REFRESH_TOKEN)
				.setScope(AuthConfig.ANIME_SCOPE)
				.setRefreshToken(refreshToken)
				.build()
	}
	
	suspend fun performTokenRequestSuspend(
		authService: AuthorizationService,
		tokenRequest: TokenRequest
	): TokensModel {
		
	}
	
	private fun getClientAuthentication(): ClientAuthentication {
		return ClientSecretPost(AuthConfig.ANIME_CLIENT_SECRET)
	}
	
	private object AuthConfig {
		//Anime - Shikimori API
		const val ANIME_BASE_URL = "https://shikimori.me/api/" // URL must end with '/'
		const val ANIME_BASE_URL_FOR_IMAGE = "https://shikimori.me"
		const val ANIME_AUTH_URI = "https://shikimori.me/oauth/authorize"
		const val ANIME_TOKEN_URI = "https://shikimori.me/oauth/token"
		const val ANIME_END_SESSION_URI = "https://shikimori.me/users/sign_out"
		const val ANIME_RESPONSE_TYPE = ResponseTypeValues.CODE
		const val ANIME_SCOPE = "user_rates,comments,topics"
		const val ANIME_CLIENT_ID = "1"
		const val ANIME_CLIENT_SECRET = "2"
		const val ANIME_REDIRECT_URI = "urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob"
		const val ANIME_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/callback"
		const val ANIME_LOGOUT_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/users/sign_out"
	}
	
}