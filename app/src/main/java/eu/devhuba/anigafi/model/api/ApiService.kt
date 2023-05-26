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
		val baseUrl = ApiConstants.BASE_URL
		val token = BuildConfig.ANIME_ACCESS_TKN
		
		//for logging
		val logging = HttpLoggingInterceptor()
		logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
		
		val clientInterceptor = okhttp3.Interceptor { chain ->
			var request: Request = chain.request()
			request = request.newBuilder().addHeader(
					"User-Agent", "ShikiAni"
				).addHeader(
					"Authorization", "Bearer $token"
				).build()
			
			chain.proceed(request)
			
		}
		
		val client =
			OkHttpClient.Builder().addInterceptor(clientInterceptor).addInterceptor(logging).build()
		
		return Retrofit.Builder()
			.baseUrl(baseUrl)
			.addConverterFactory(GsonConverterFactory.create())
			.client(client)
			.build()
		
	}
	
	val animeApi: ShikimoriApi = getRetrofit().create(ShikimoriApi::class.java)
	
	// CONVERT FROM CURL

//    val client = OkHttpClient()
//
//    val requestBody = MultipartBody.Builder()
//        .setType(MultipartBody.FORM)
//        .addFormDataPart("grant_type", "authorization_code")
//        .addFormDataPart("client_id", "5pAWAg3DHROjxoKnhvuj_N_I4dH4fXIL9pbzyXfCBD8")
//        .addFormDataPart("client_secret", "BTLIevXzWV_w-jP1ueZiv6m72L-O0wf4cY67waO1Ar8")
//        .addFormDataPart("code", "zTKmDNLTBGCV12gQxn14o0NaHKY_v4T3n7S7tYbX_BY")
//        .addFormDataPart("redirect_uri", "urn:ietf:wg:oauth:2.0:oob")
//        .build()
//
//    val request = Request.Builder()
//        .url("https://shikimori.me/oauth/token")
//        .post(requestBody)
//        .header("User-Agent", "ShikiAni")
//        .build()
//
//    client.newCall(request).execute().use { response ->
//        if (!response.isSuccessful) throw IOException("Unexpected code $response")
//        val result = response.body!!.string()
//        Log.i("this","result -> $result")
//    }
	
}