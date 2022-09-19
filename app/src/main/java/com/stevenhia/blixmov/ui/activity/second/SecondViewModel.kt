package com.stevenhia.blixmov.ui.activity.second

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

class SecondViewModel : ViewModel() {
    private val _movieByGenre = MutableLiveData<Status<List<MovieData>>>()
    val listMovieByGenre: LiveData<Status<List<MovieData>>> = _movieByGenre

    fun getIdMovieByGenre(genreId: String) {
        RetrofitConfig.getRetrofitService().getMovieByGenre(genreId)
            .enqueue(object : Callback<MovieResponse> {
                override fun onResponse(
                    call: Call<MovieResponse>,
                    response: Response<MovieResponse>
                ) {
                    if (response.isSuccessful) {
                        _movieByGenre.postValue(Status.success(response.body()?.listResult))
                    } else if (response.code() == 404) {
                        _movieByGenre.value = Status.error404("", data = null)
                    }
                }

                override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                    _movieByGenre.value = Status.error(t.message.toString(), data = null)
                }
            })
    }
}