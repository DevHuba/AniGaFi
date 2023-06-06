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

    const val SHARED_PREFERENCES_NAME = "AUTH_STATE_PREFERENCE"
    const val AUTH_STATE = "AUTH_STATE"
    const val MESSAGE_DIGEST_ALGORITHM = "SHA-256"
    const val URL_AUTH_REDIRECT = "eu.devhuba.anigafi:/oauth2redirect"


}

//
//	val SCOPE_PROFILE = "profile"
//	val SCOPE_EMAIL = "email"
//	val SCOPE_OPENID = "openid"
//	val SCOPE_DRIVE = "https://www.googleapis.com/auth/drive"
//
//	val DATA_PICTURE = "picture"
//	val DATA_FIRST_NAME = "given_name"
//	val DATA_LAST_NAME = "family_name"
//	val DATA_EMAIL = "email"
//
//	val CLIENT_ID = "1080220280079-d0c0ba1076kdsr3d0pbuvd7e3npj1ket.apps.googleusercontent.com"
//	val CODE_VERIFIER_CHALLENGE_METHOD = "S256"
//
//	val URL_AUTHORIZATION = "https://accounts.google.com/o/oauth2/v2/auth"
//	val URL_TOKEN_EXCHANGE = "https://www.googleapis.com/oauth2/v4/token"
//val URL_AUTH_REDIRECT = "eu.devhuba.anigafi:/oauth2redirect"
//	val URL_API_CALL = "https://www.googleapis.com/drive/v2/files"
//	val URL_LOGOUT = "https://accounts.google.com/o/oauth2/revoke?token="
//
//	val URL_LOGOUT_REDIRECT = "com.ptruiz.authtest:/logout"
