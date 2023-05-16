package eu.devhuba.anigafi.model.api

import eu.devhuba.anigafi.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Request
import retrofit2.Retrofit

object ApiService {
    private const val BASE_URL = "https://shikimori.me/api/"

    private fun getRetrofit(): Retrofit {
        val clientId = BuildConfig.ANIME_CLIENT_ID
        val secret = BuildConfig.ANIME_SECRET
        val code = BuildConfig.ANIME_AUTH_CODE
        val redirectUri = "urn:ietf:wg:oauth:2.0:oob"

        val clientInterceptor = okhttp3.Interceptor { chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder().add
            
        }


    }
}