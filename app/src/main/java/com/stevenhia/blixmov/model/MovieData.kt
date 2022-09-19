package com.stevenhia.blixmov.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("results")
    var listResult: ArrayList<MovieData>
)

data class MovieData(
    @SerializedName("id")
    var id: Int? = 0,

    @SerializedName("adult")
    val adult: Boolean,

    @SerializedName("poster_path")
    var posterPath: String?,

    @SerializedName("backdrop_path")
    var backdropPath: String? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("vote_count")
    var voteCount: Int? = 0,

    @SerializedName("vote_average")
    var voteAverage: Float?,

    @SerializedName("genre_ids")
    var genreIds: List<Int>,

    @SerializedName("original_language")
    var originalLanguage: String? = "",

    @SerializedName("release_date")
    var releaseDate: String? = null,

    @SerializedName("runtime")
    var runtime: Int? = null,

    @SerializedName("overview")
    var overview: String? = null,

    @SerializedName("genres")
    var genres: List<GenreData>
)
