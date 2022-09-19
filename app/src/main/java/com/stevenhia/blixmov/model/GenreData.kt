package com.stevenhia.blixmov.model

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val listGenre: List<GenreData>
)

data class GenreData(
    @SerializedName("id")
    var id: Int? = 0,
    @SerializedName("name")
    var name: String? = null
)
