package com.stevenhia.blixmov.network

import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {

    //Genre
    @GET("genre/movie/list?api_key=$API_KEY")
    fun getListGenre(): Call<GenreResponse>

    @GET("discover/movie?api_key=$API_KEY")
    fun getMovieByGenre(@Query("with_genres") GenreId: String): Call<MovieResponse>

    //Movie
    @GET("movie/upcoming?api_key=$API_KEY")
    fun getUpcomingMovies(): Call<MovieResponse>

    @GET("movie/popular?api_key=$API_KEY")
    fun getPopularMovies(): Call<MovieResponse>

    @GET("movie/top_rated?api_key=$API_KEY")
    fun getTopRatedMovies(): Call<MovieResponse>

    @GET("movie/{movieId}?api_key=$API_KEY")
    fun getMovieDetails(@Path("movieId") movieId: Int): Call<MovieData>

    //REVIEW MOVIE
    @GET("movie/{movie_id}/reviews?api_key=$API_KEY")
    fun getReview(@Path("movie_id") movieId: Int): Call<ReviewResponse>

    //PEOPLE
    @GET("movie/{movie_id}/credits?api_key=$API_KEY")
    fun getMovieCredit(@Path("movie_id") movieId: Int): Call<CreditResponse>


    //VIDEO TRAILER
    @GET("movie/{movie_id}/videos?api_key=$API_KEY")
    fun getMovieTrailer(@Path("movie_id") movieId: Int): Call<VideoResponse>

    companion object {
        const val API_KEY = BuildConfig.TMDB_API_KEY
    }

}