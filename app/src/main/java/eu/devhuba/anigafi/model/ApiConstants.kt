package eu.devhuba.anigafi.model

import eu.devhuba.anigafi.BuildConfig
import net.openid.appauth.ResponseTypeValues

object ApiConstants {
	//Anime - Shikimori API
	const val ANIME_BASE_URL = "https://shikimori.me/api/" // URL must end with '/'
	const val ANIME_BASE_URL_FOR_IMAGE = "https://shikimori.me"
	const val ANIME_AUTH_URI = "https://shikimori.me/oauth/authorize"
	const val ANIME_TOKEN_URI = "https://shikimori.me/oauth/token"
	const val ANIME_END_SESSION_URI = "https://shikimori.me/users/sign_out"
	const val ANIME_RESPONSE_TYPE = ResponseTypeValues.CODE
	const val ANIME_SCOPE = "user_rates,comments,topics"
	const val ANIME_CLIENT_ID = BuildConfig.ANIME_CLIENT_ID
	const val ANIME_CLIENT_SECRET = BuildConfig.ANIME_SECRET
	const val ANIME_REDIRECT_URI = "urn%3Aietf%3Awg%3Aoauth%3A2.0%3Aoob"
	const val ANIME_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/callback"
	const val ANIME_LOGOUT_CALLBACK_URL = "eu.devhuba.anigafi://shikimori.me/logout_callback"
}