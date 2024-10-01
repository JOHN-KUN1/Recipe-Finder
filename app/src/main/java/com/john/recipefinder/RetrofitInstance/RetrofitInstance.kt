package com.john.recipefinder.RetrofitInstance

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val baseUrl = "https://www.themealdb.com/api/json/v1/1/"

    fun getRetrofitInstance() : Retrofit{
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

}