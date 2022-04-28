package com.example.rickandmortyguide

import com.google.gson.annotations.SerializedName
import kotlin.collections.ArrayList
//класс для подробной информации о персонаже
class ResultCharacter(
    @SerializedName("id")
    var id: Int, @SerializedName("name")
    var name: String, @SerializedName("status")
    var status: String, @SerializedName("species")
    var species: String, @SerializedName("gender")
    var gender: String, @SerializedName("location")
    var location: HashMap<String, String>, @SerializedName("image")
    var image: String, @SerializedName("episode")
    var episode: ArrayList<String>
    )