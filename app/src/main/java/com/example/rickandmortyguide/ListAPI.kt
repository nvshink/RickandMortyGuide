package com.example.rickandmortyguide

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ListAPI {

    @GET("character/")
    fun getCharacters(
        @Query("page") page: Int
    ): Flowable<Characters>

}