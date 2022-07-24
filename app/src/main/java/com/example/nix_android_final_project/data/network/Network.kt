package com.example.nix_android_final_project.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object Network {
    // Your API key
    private const val API_KEY = "454669a012e954a5b8d7458b"

    private val client = OkHttpClient.Builder().build()

    val api: ExchangeServiceAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/$API_KEY/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build()
            .create(ExchangeServiceAPI::class.java)
    }
}
