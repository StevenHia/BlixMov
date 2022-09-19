package com.stevenhia.blixmov.ui.activity.second

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.stevenhia.blixmov.adapter.HomeAdapter
import com.stevenhia.blixmov.databinding.ActivitySecondBinding
import com.stevenhia.blixmov.utils.APIStatus

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var secondAdapter: HomeAdapter

    private var genreId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        secondAdapter = HomeAdapter()

        genreId = intent.getStringExtra(INTENT_GENRE_ID)
        if (genreId != null) {
            secondViewModel.getIdMovieByGenre(genreId!!)
        }

        setViewModel()
        setRecyclerView()

    }

    private fun setRecyclerView() {
        binding.rvMovie.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@SecondActivity, 2)
            adapter = secondAdapter
        }
    }

    private fun setViewModel() {
        secondViewModel.listMovieByGenre.observe(this) {
            when (it.status) {
                APIStatus.SUCCESS -> {
                    if (it.data != null) {
                        secondAdapter.setData(it.data)
                        binding.layoutConstNoNetwork.visibility = View.GONE
                    }
                }

                APIStatus.ERROR404 -> {
                    binding.layoutConstNoNetwork.visibility = View.VISIBLE
                }

                APIStatus.ERROR -> {}
                else -> {}
            }
        }
    }

    companion object {
        const val INTENT_GENRE_ID = "intent_genre_id"
    }
}