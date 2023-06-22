package eu.devhuba.anigafi.model.auth

import android.net.Uri
import androidx.core.net.toUri
import eu.devhuba.anigafi.BuildConfig
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
import java.net.URLDecoder
import kotlin.coroutines.suspendCoroutine


object AppAuth {
	
	private val serviceConfiguration = AuthorizationServiceConfiguration(
		Uri.parse(AuthConfig.ANIME_AUTH_URI),
		Uri.parse(AuthConfig.ANIME_TOKEN_URI),
		null,
		Uri.parse(AuthConfig.ANIME_END_SESSION_URI)
	)
	
	fun getAuthRequest(): AuthorizationRequest {
		val redirectUri = AuthConfig.ANIME_REDIRECT_URI.toUri()
		
		return AuthorizationRequest.Builder(
			serviceConfiguration,
			AuthConfig.ANIME_CLIENT_ID,
			AuthConfig.ANIME_RESPONSE_TYPE,
			redirectUri
		).setScope(URLDecoder.decode(AuthConfig.ANIME_SCOPE, "UTF-8")).build()
		
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
	
	class TokensModel(
		val accessToken: String,
		val refreshToken: String,
		val idToken: String
	)
	
	suspend fun performTokenRequestSuspend(
		authService: AuthorizationService,
		tokenRequest: TokenRequest
	): TokensModel {
		return suspendCoroutine { continuation ->
			authService.performTokenRequest(
				tokenRequest,
				getClientAuthentication()
			) { response, ex ->
				when {
					response != null -> {
						// Token request successful
						val tokens = TokensModel(
							accessToken = response.accessToken.orEmpty(),
							refreshToken = response.refreshToken.orEmpty(),
							idToken = response.idToken.orEmpty()
						)
						continuation.resumeWith(Result.success(tokens))
					}
					// Token request unsuccessful
					ex != null -> {
						continuation.resumeWith(Result.failure(ex))
					}
					
					else -> error("unreachable")
				}
				
			}
		}
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
		const val ANIME_SCOPE = "user_rates+comments+topics"
		const val ANIME_CLIENT_ID = BuildConfig.ANIME_CLIENT_ID
		const val ANIME_CLIENT_SECRET = BuildConfig.ANIME_SECRET
		const val ANIME_REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob"
		const val ANIME_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/callback"
		const val ANIME_LOGOUT_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/users/sign_out"
	}
	
}