package com.stevenhia.blixmov.model

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("results")
    var listVideoTrailer: List<VideoData>
)

data class VideoData(
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("key")
    var key: String? = null
)
