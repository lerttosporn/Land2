package com.example.mobiletest

import com.example.mobiletest.data.WeCity
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.SplittableRandom

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

interface ApiService {
    @GET("weather")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") appId: String = "594be88dd7e12b8403895a4e3642ca29"
    ): Call<WeCity>


    companion object {
        fun retrofitBuild(): ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}