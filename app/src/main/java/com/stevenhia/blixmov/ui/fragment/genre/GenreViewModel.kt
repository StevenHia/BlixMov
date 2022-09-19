package com.stevenhia.blixmov.ui.fragment.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stevenhia.blixmov.model.GenreData
import com.stevenhia.blixmov.model.GenreResponse
import com.stevenhia.blixmov.network.RetrofitConfig
import com.stevenhia.blixmov.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GenreViewModel : ViewModel() {
    private val _genre = MutableLiveData<Status<List<GenreData>>>()
    val getListGenre: LiveData<Status<List<GenreData>>> = _genre

    init {
        fetchGenreMovie()
    }

    private fun fetchGenreMovie() {
        RetrofitConfig.getRetrofitService().getListGenre()
            .enqueue(object : Callback<GenreResponse> {
                override fun onResponse(
                    call: Call<GenreResponse>,
                    response: Response<GenreResponse>
                ) {
                    if (response.isSuccessful) {
                        _genre.postValue(Status.success(response.body()?.listGenre))
                    } else if (response.code() == 404) {
                        _genre.postValue(Status.error404("", data = null))
                    }
                }

                override fun onFailure(call: Call<GenreResponse>, t: Throwable) {
                    _genre.postValue(Status.error(t.message.toString(), data = null))
                }

            })
    }
}