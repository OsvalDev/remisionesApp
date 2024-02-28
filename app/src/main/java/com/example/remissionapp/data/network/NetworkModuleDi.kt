package com.example.remissionapp.data.network

import com.example.remissionapp.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModuleDi {
    private val gsonFactory: GsonConverterFactory = GsonConverterFactory.create()
    private val okHttpClient: OkHttpClient = OkHttpClient()

    operator fun invoke(): apiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(NetworkModuleDi.okHttpClient)
            .addConverterFactory(NetworkModuleDi.gsonFactory)
            .build()
            .create(apiService::class.java)
    }
}