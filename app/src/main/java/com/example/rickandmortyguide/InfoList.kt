package com.example.rickandmortyguide

import com.google.gson.annotations.SerializedName
//Класс для информации о предыдущей и следующих страницах отноистельно настоящей
class InfoList(
    @SerializedName("next")
    var next: String,
    @SerializedName("prev")
    var prev: String,
)