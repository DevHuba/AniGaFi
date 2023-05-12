package eu.devhuba.anigafi.model.api

import eu.devhuba.anigafi.model.AnimeApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ShikimoriApi{
	@GET("anime")
	fun getAnime(@Query("nameStartsWith") name: String) : Call<AnimeApiResponse>
}