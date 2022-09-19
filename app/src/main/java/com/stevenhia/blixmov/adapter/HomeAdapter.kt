package com.stevenhia.blixmov.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemMovieBinding
import com.stevenhia.blixmov.model.MovieData
import com.stevenhia.blixmov.ui.activity.detail.DetailActivity

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var listMovie = ArrayList<MovieData>()

    fun setData(items: List<MovieData>) {
        listMovie.clear()
        listMovie.addAll(items)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MovieData) {
            Picasso.get()
                .load(BuildConfig.BASE_URL_IMAGE + data.posterPath)
                .placeholder(R.drawable.logo)
                .into(binding.imageViewSingleMovie)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.INTENT_ID, data.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listMovie[position])
    }

    override fun getItemCount(): Int {
        return if (listMovie.size > 6) 6 else listMovie.size
    }
}