package eu.devhuba.anigafi.model.api

import android.util.Log
import eu.devhuba.anigafi.model.AnimeApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShikimoriApiRepo(private val animeApi: ShikimoriApi) {
    val animes = MutableStateFlow<NetworkResult<List<AnimeApiResponse>>>(NetworkResult.Initial())

    fun query() {
        animes.value = NetworkResult.Loading()
        animeApi.getCalendar()
            .enqueue(object : Callback<List<AnimeApiResponse>> {
                override fun onResponse(
                    call: Call<List<AnimeApiResponse>>,
                    response: Response<List<AnimeApiResponse>>
                ) {
                    if (response.isSuccessful)
                        response.body()
                            ?.let {
                                animes.value = NetworkResult.Success(it)
                                Log.i("this", "it -> $it")
                            }
                    else {
                        animes.value = NetworkResult.Error(response.message())
                    }
                }

                override fun onFailure(
                    call: Call<List<AnimeApiResponse>>,
                    t: Throwable
                ) {
                    t.localizedMessage?.let {
                        animes.value = NetworkResult.Error(it)
                        Log.i("this", "it -> $it")
                    }
                    t.printStackTrace()
                }

            })
    }
}