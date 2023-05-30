package eu.devhuba.anigafi.model

import eu.devhuba.anigafi.BuildConfig

object ApiConstants {
    //Anime - Shikimori API
    const val BASE_URL = "https://shikimori.me/api/" // URL must end with '/'
    const val BASE_URL_FOR_IMAGE = "https://shikimori.me"
    const val ANIME_AUTH_URI =
        "https://shikimori.me/oauth/authorize?client_id=${
            BuildConfig
                .ANIME_CLIENT_ID
        }&redirect_uri=urn%3Aietf%3Awg" +
                "%3Aoauth" +
                "%3A2.0%3Aoob&response_type=code&scope=user_rates+comments+topics"
}