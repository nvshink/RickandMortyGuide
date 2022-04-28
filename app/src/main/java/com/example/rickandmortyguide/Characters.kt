package com.example.rickandmortyguide

import com.google.gson.annotations.SerializedName

class Characters (@SerializedName("info")val infoList: InfoList, @SerializedName("results")
                  val resultLists: List<ResultList>)