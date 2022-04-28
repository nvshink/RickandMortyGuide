package com.example.rickandmortyguide

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ListService {
    val BASE_URL : String = "https://rickandmortyapi.com/api/"
    var retrofit : Retrofit
    var mInstance: ListService ?= null
    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    
    fun getInstance(): ListService? {
        if (mInstance == null) mInstance = ListService()
        return mInstance
    }
    //получение API
    fun getCharactersApi(): ListAPI? {
        return retrofit.create(ListAPI::class.java)
    }
}