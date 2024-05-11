package hugsmaker.com.noteapp.api.client

import hugsmaker.com.noteapp.api.service.NoteApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://your-api-base-url.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val noteApiService: NoteApiService by lazy {
        retrofit.create(NoteApiService::class.java)
    }
}
