package com.stevenhia.blixmov.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("results")
    var listReview: List<ReviewData>
)

data class ReviewData(
    @SerializedName("author")
    var author: String? = null,

    @SerializedName("content")
    var content: String? = null,

    @SerializedName("id")
    var idReview: String? = null,


    )
