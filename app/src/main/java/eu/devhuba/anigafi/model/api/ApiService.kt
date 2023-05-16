package eu.devhuba.anigafi.model.api

import android.os.Build
import eu.devhuba.anigafi.BuildConfig
import retrofit2.Retrofit

object ApiService {
	private const val BASE_URL = "https://shikimori.me/api/"
	
	private fun getRetrofit(): Retrofit {
		val authToken = BuildConfig.ANIME_AUTH_TOKEN
		

	}
}