package eu.devhuba.anigafi.model.auth

import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import timber.log.Timber

class AuthRepository {
	
	fun corruptAccessToken() {
		TokenStorage.accessToken = "fake token"
	}
	
	fun logout() {
		TokenStorage.accessToken = null
		TokenStorage.refreshToken = null
		TokenStorage.idToken = null
	}
	
	fun getAuthRequest(): AuthorizationRequest {
		return AppAuth.getAuthRequest()
	}
	
	fun getEndSessionRequest(): EndSessionRequest {
		return AppAuth.getEndSessionRequest()
	}
	
	suspend fun performTokenRequest(
		authService: AuthorizationService,
		tokenRequest: TokenRequest
	) {
		
		Timber.tag("oauth").d("authService -> $authService +treq $tokenRequest")
		
		val tokens = AppAuth.peformTokenRequestSuspend(authService, tokenRequest)
		
		TokenStorage.accessToken = tokens.accessToken
		TokenStorage.refreshToken = tokens.refreshToken
		TokenStorage.idToken = tokens.idToken
		
		Timber.tag("oauth")
				.d("6. Tokens accepted:\n access = ${tokens.accessToken}\nrefreshToken = ${tokens.refreshToken}\nidToken = ${tokens.idToken}")
		
		
	}
}