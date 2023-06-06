package eu.devhuba.anigafi.model.api

import eu.devhuba.anigafi.BuildConfig
import eu.devhuba.anigafi.model.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    //TODO: Delete this line if all works 
//    private const val BASE_URL = "https://shikimori.me/api"

    private fun getRetrofit(): Retrofit {
        val baseUrl = ApiConstants.ANIME_BASE_URL
        val token = BuildConfig.ANIME_ACCESS_TKN

        //for logging
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val clientInterceptor = okhttp3.Interceptor { chain ->
            var request: Request = chain.request()
            request = request.newBuilder()
                .addHeader(
                    "User-Agent", "ShikiAni"
                )
                .addHeader(
                    "Authorization", "Bearer $token"
                )
                .build()

            chain.proceed(request)

        }

        val client =
            OkHttpClient.Builder()
                .addInterceptor(clientInterceptor)
                .addInterceptor(logging)
                .build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    }

    val animeApi: ShikimoriApi = getRetrofit().create(ShikimoriApi::class.java)

}