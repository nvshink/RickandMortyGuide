package com.example.rickandmortyguide

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterAPI {

    @GET("character/{id}")
    fun getCharacter(
        @Path("id") id: Int
    ): Flowable<ResultCharacter>
}