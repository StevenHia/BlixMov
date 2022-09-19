package com.stevenhia.blixmov.ui.activity.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevenhia.blixmov.model.*
import com.stevenhia.blixmov.network.RetrofitConfig
import com.stevenhia.blixmov.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivityViewModel : ViewModel() {
    private var _detailMovie = MutableLiveData<Status<MovieData>>()

    private var _listVideoTrailer = MutableLiveData<Status<List<VideoData>>>()
    /*val getListVideoTrailer: = */

    private var _listReviewMovie = MutableLiveData<List<ReviewData>>()
    private var _listCrew = MutableLiveData<List<Crew>>()
    private var _listCast = MutableLiveData<List<Cast>>()

    private fun fetchDetail(movieId: Int) {
        RetrofitConfig.getRetrofitService().getMovieDetails(movieId)
            .enqueue(object : Callback<MovieData> {
                override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                    if (response.isSuccessful) {
                        _detailMovie.postValue(Status.success(response.body()))
                    }
                }

                override fun onFailure(call: Call<MovieData>, t: Throwable) {
                    _detailMovie.postValue(Status.error(t.message.toString(), null))
                }

            })
    }

    fun getDetail(movieId: Int): LiveData<Status<MovieData>> {
        fetchDetail(movieId)
        return _detailMovie
    }

    fun fetchVideoTrailer(movieId: Int): LiveData<Status<List<VideoData>>> {
        RetrofitConfig.getRetrofitService().getMovieTrailer(movieId)
            .enqueue(object : Callback<VideoResponse> {
                override fun onResponse(
                    call: Call<VideoResponse>,
                    response: Response<VideoResponse>
                ) {
                    if (response.isSuccessful) {
                        _listVideoTrailer.postValue(Status.success(response.body()?.listVideoTrailer))
                    } else if (response.code() == 404) {
                        _listVideoTrailer.postValue(Status.error404("", data = null))
                    }
                }

                override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
                    _listVideoTrailer.postValue(Status.error(t.message.toString(), null))
                }

            })

        return _listVideoTrailer
    }

    fun getListCast(movieId: Int): LiveData<List<Cast>> {
        RetrofitConfig.getRetrofitService().getMovieCredit(movieId)
            .enqueue(object : Callback<CreditResponse> {
                override fun onResponse(
                    call: Call<CreditResponse>,
                    response: Response<CreditResponse>
                ) {
                    if (response.isSuccessful) {
                        _listCast.value = response.body()?.cast
                    }
                }

                override fun onFailure(call: Call<CreditResponse>, t: Throwable) {
                    Log.d("On Failure", t.message.toString())
                }

            })
        return _listCast
    }

    fun getListCrew(movieId: Int): LiveData<List<Crew>> {
        RetrofitConfig.getRetrofitService().getMovieCredit(movieId)
            .enqueue(object : Callback<CreditResponse> {
                override fun onResponse(
                    call: Call<CreditResponse>,
                    response: Response<CreditResponse>
                ) {
                    if (response.isSuccessful) {
                        _listCrew.value = response.body()?.crew
                    }
                }

                override fun onFailure(call: Call<CreditResponse>, t: Throwable) {
                    Log.d("On Failure", t.message.toString())
                }

            })
        return _listCrew
    }

    fun getListReview(movieId: Int): LiveData<List<ReviewData>> {
        RetrofitConfig.getRetrofitService().getReview(movieId)
            .enqueue(object : Callback<ReviewResponse> {
                override fun onResponse(
                    call: Call<ReviewResponse>,
                    response: Response<ReviewResponse>
                ) {
                    if (response.isSuccessful) {
                        _listReviewMovie.value = response.body()?.listReview
                    }
                }

                override fun onFailure(call: Call<ReviewResponse>, t: Throwable) {
                    Log.d("On Failure", t.message.toString())
                }

            })
        return _listReviewMovie
    }
}