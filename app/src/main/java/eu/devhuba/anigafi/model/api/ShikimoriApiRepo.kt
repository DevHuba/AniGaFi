package eu.devhuba.anigafi.model.api

import eu.devhuba.anigafi.model.AnimeApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShikimoriApiRepo(private val animeApi: ShikimoriApi) {
    val animes = MutableStateFlow<NetworkResult<AnimeApiResponse>>(NetworkResult.Initial())

    fun query() {
        animes.value = NetworkResult.Loading()
        animeApi.getCalendar().enqueue(object : Callback<AnimeApiResponse> {
            override fun onResponse(
                call: Call<AnimeApiResponse>,
                response: Response<AnimeApiResponse>
            ) {
                if (response.isSuccessful)
                    response.body()?.let {
                        animes.value = NetworkResult.Success(it)
                    }
                else
                    animes.value = NetworkResult.Error(response.message())
            }

            override fun onFailure(call: Call<AnimeApiResponse>, t: Throwable) {
                t.localizedMessage?.let {
                    animes.value = NetworkResult.Error(it)
                }
                t.printStackTrace()
            }

        })
    }
}