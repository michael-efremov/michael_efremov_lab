package ru.mike.lab.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.mike.lab.mvp.models.Post
import ru.mike.lab.mvp.models.ResponsePosts

interface Api {

    companion object {

        const val QUERY_JSON = "json"

        private const val BASE_URL = "http://developerslife.ru/"

        fun getApi(): Api {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build().create(Api::class.java)
        }
    }

    @GET("random")
    suspend fun getRandomPost(@Query(QUERY_JSON) type: Boolean = true): Post

    @GET("{section}/{page}")
    suspend fun getSection(
        @Path("section") section: String = "latest",
        @Path("page") page: Int = 0,
        @Query(QUERY_JSON) type: Boolean = true
    ): ResponsePosts

}