package com.example.rickandmortyguide

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CharacterService {

        val BASE_URL : String = "https://rickandmortyapi.com/api/"
        var retrofit : Retrofit
        var mInstance: CharacterService ?= null
        init {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        fun getInstance(): CharacterService? {
            if (mInstance == null) mInstance = CharacterService()
            return mInstance
        }
        //получение API
        fun getCharacterApi(): CharacterAPI? {
            return retrofit.create(CharacterAPI::class.java)
        }

}