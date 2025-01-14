package com.example.currencyconverter.network

import CurrencyApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: CurrencyApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.exchangeratesapi.io/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)
    }
}
