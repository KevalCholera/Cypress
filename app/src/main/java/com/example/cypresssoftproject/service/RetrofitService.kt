package com.example.cypresssoftproject.service

import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.model.singleimages.DashboardSingleImagesResponse
import com.example.cypresssoftproject.utils.apiUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {

    @GET("albums")
    @Headers("accept:application/json")
    suspend fun getAlbumList(): Response<List<DashboardAlbumResponse>>

    @GET("photos")
    @Headers("accept:application/json")
    suspend fun getFullImageList(): Response<List<DashboardImagesResponse>>

    @GET("photos")
    @Headers("accept:application/json")
    suspend fun getSingleImageList(
        @Query("albumId") albumId: String
    ): Response<List<DashboardSingleImagesResponse>>

    companion object {

        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var httpClient = OkHttpClient.Builder().addInterceptor(logging)

        var retrofitService: RetrofitService? = null
        fun getInstance(): RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
//                    .baseUrl("https://howtodoandroid.com/")
//                    .baseUrl("https://reqres.in/api/")
                    .baseUrl(apiUrl.API_URL)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }

}