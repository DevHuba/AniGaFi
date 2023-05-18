package eu.devhuba.anigafi.model.api

import eu.devhuba.anigafi.model.AnimeApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface ShikimoriApi {
    @GET("/calendar")
    fun getCalendar(): Call<AnimeApiResponse>

//    @GET("/animes/search")
//    fun getCalendar(@Query("q") animeName: String): Call<AnimeApiResponse>

//    @GET("/animes/{id}")
//    fun getSpecificAnimeById(@Path("id") id: String): Call<CurrentAnime>

//    @FormUrlEncoded
//    @POST("/oauth/token")
//    suspend fun getToken(
//        @Field("grant_type") grantType: String,
//        @Field("client_id") clientId: String,
//        @Field("client_secret") clientSecret: String,
//        @Field("code") code: String,
//        @Field("redirect_uri") redirectUri: String,
//    ): String

}


