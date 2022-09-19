package com.stevenhia.blixmov.ui.activity.detail

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.adapter.CastAdapter
import com.stevenhia.blixmov.adapter.CrewAdapter
import com.stevenhia.blixmov.adapter.DetailVideoAdapter
import com.stevenhia.blixmov.adapter.ReviewsAdapter
import com.stevenhia.blixmov.databinding.ActivityDetailBinding
import com.stevenhia.blixmov.model.MovieData
import com.stevenhia.blixmov.utils.APIStatus

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var videoAdapter: DetailVideoAdapter
    private lateinit var castAdapter: CastAdapter
    private lateinit var crewAdapter: CrewAdapter
    private lateinit var reviewAdapter: ReviewsAdapter

    private val detailViewModel: DetailActivityViewModel by viewModels()

    private var movieId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = View.SCREEN_STATE_ON
        }
        videoAdapter = DetailVideoAdapter()
        castAdapter = CastAdapter()
        crewAdapter = CrewAdapter()
        reviewAdapter = ReviewsAdapter()

        movieId = intent.getIntExtra(INTENT_ID, 0)
        setViewModel(movieId!!)

        setRecyclerView()
    }

    private fun setViewModel(movieId: Int) {
        detailViewModel.getDetail(movieId).observe(this) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        getDataDetail(it.data)
                    }
                }
                APIStatus.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        detailViewModel.fetchVideoTrailer(movieId).observe(this) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        videoAdapter.setData(it.data)
                        Log.d("CHECK DATA VIDEO TRAILER", it.data.toString())
                        binding.viewStatusVideoTrailer.visibility = View.GONE
                    } else {
                        binding.viewStatusVideoTrailer.visibility = View.VISIBLE
                    }
                }
                APIStatus.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        }

        detailViewModel.getListCast(movieId).observe(this) {
            castAdapter.setData(it)
            Log.d("CHECK RV CAST", it.toString())
        }

        detailViewModel.getListCrew(movieId).observe(this) {
            crewAdapter.setData(it)
        }

        detailViewModel.getListReview(movieId).observe(this) {
            reviewAdapter.setData(items = it)
        }
    }

    private fun getDataDetail(data: MovieData) {
        Picasso.get().load(BuildConfig.BASE_URL_BACKDROP + data.backdropPath)
            .into(binding.imageViewSingleMovieDetails)

        binding.movieOverviewMovieDetails.text = data.overview
        binding.dateSingleMovieDetails.text = data.releaseDate
        binding.titleSingleMovieDetails.text = data.title

        if (data.adult) {
            binding.tvAdult.text = getText(R.string.number_18)
        } else {
            binding.tvAdult.text = getText(R.string.number_13)
        }
    }

    private fun setRecyclerView() {
        binding.rvVideoTrailer.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = videoAdapter
        }
        /*videoAdapter.setOnItemClickListener(object : AdapterItemClickListener<VideoData> {
            override fun onItemClicked(position: Int, model: VideoData) {

            }

        })*/

        //RecyclerView Cast
        binding.rvCastPeople.setHasFixedSize(true)

        binding.rvCastPeople.layoutManager =
            LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvCastPeople.adapter = castAdapter

        //RecyclerView Crew
        binding.rvCrewPeople.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = crewAdapter
        }

        //RecyclerView Reviews
        binding.rvReview.apply {
            setHasFixedSize(true)
            layoutManager =
                LinearLayoutManager(this@DetailActivity)
            adapter = reviewAdapter
        }
    }

    companion object {
        const val INTENT_ID = "intent_id"
    }
}