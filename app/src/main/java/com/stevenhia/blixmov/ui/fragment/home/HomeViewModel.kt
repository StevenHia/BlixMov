package com.stevenhia.blixmov.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevenhia.blixmov.model.MovieData
import com.stevenhia.blixmov.model.MovieResponse
import com.stevenhia.blixmov.network.RetrofitConfig
import com.stevenhia.blixmov.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private var loadingData = MutableLiveData<Boolean>()

    private val _upComingMovie = MutableLiveData<List<MovieData>>()
    fun upComingMovie(): LiveData<List<MovieData>> {
        loadingData.value = true
        RetrofitConfig.getRetrofitService().getUpcomingMovies()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        _upComingMovie.postValue(response.body()!!.listResult)
                        loadingData.value = false
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    loadingData.value = false
                }

            })
        return _upComingMovie
    }

    private val _popularMovie = MutableLiveData<Status<List<MovieData>>>()
    fun getListPopularMovies(): LiveData<Status<List<MovieData>>> {
        fetchPopularMovie()
        return _popularMovie
    }

    private fun fetchPopularMovie() {
        RetrofitConfig.getRetrofitService().getPopularMovies()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        _popularMovie.postValue(Status.success(response.body()?.listResult))
                    } else if (response.code() == 400) {
                        _popularMovie.postValue(Status.error404("Data Not Found", null))
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    _popularMovie.postValue(Status.error404(t.message.toString(), null))
                }

            })
    }


    private val _topRatedMovie = MutableLiveData<Status<List<MovieData>>>()
    val getListTopRatedMovies: LiveData<Status<List<MovieData>>> = _topRatedMovie

    init {
        fetchTopRatedMovie()
    }

    private fun fetchTopRatedMovie() {
        RetrofitConfig.getRetrofitService().getTopRatedMovies()
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        _topRatedMovie.postValue(Status.success(response.body()?.listResult))
                    } else if (response.code() == 400) {
                        _topRatedMovie.postValue(Status.error404("Data Not Found", null))
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    _topRatedMovie.postValue(Status.error404(t.message.toString(), null))
                }

            })
    }
}