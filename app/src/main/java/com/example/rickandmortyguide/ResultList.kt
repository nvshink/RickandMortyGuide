package com.example.rickandmortyguide

import com.google.gson.annotations.SerializedName

//класс для информации о персонаже для списка
class ResultList(
    @SerializedName("id")
    var id: Int, @SerializedName("name")
    var name: String, @SerializedName("species")
    var species: String, @SerializedName("gender")
    var gender: String, @SerializedName("image")
    var image: String
)