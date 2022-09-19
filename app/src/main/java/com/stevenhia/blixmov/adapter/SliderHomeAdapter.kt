package com.stevenhia.blixmov.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemSliderHomeBinding
import com.stevenhia.blixmov.model.MovieData
import com.stevenhia.blixmov.ui.activity.detail.DetailActivity

class SliderHomeAdapter : SliderViewAdapter<SliderHomeAdapter.ViewHolder>() {

    private var listSlider = ArrayList<MovieData>()

    fun setData(items: List<MovieData>) {
        listSlider.clear()
        listSlider.addAll(items)
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemSliderHomeBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun bind(data: MovieData) {
            Picasso.get()
                .load(BuildConfig.BASE_URL_IMAGE + data.posterPath)
                .into(binding.imageViewSingleMovieSlider)

            binding.tvTitleSlider.text = data.title
            binding.tvReleaseDateSlider.text = data.releaseDate
            if (data.adult) {
                binding.adultCheckMovieSlider.text = itemView.context.getText(R.string.number_18)
            } else {
                binding.adultCheckMovieSlider.text = itemView.context.getText(R.string.number_13)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.INTENT_ID, data.id)
                itemView.context.startActivity(intent)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
        return ViewHolder(
            ItemSliderHomeBinding.inflate(
                LayoutInflater.from(parent!!.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder?, position: Int) {
        viewHolder?.bind(listSlider[position])
    }

    override fun getCount(): Int {
        return if (listSlider.size > 5) 5 else listSlider.size
    }

}