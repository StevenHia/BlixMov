package com.stevenhia.blixmov.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemVideoTrailerBinding
import com.stevenhia.blixmov.model.VideoData

class DetailVideoAdapter : RecyclerView.Adapter<DetailVideoAdapter.ViewHolder>() {
    private val listVideo = ArrayList<VideoData>()

    fun setData(items: List<VideoData>) {
        listVideo.clear()
        listVideo.addAll(items)
        this.notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: ItemVideoTrailerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: VideoData) {
            Picasso.get().load("https://img.youtube.com/vi/" + data.key + "/hqdefault.jpg")
                .placeholder(R.drawable.logo)
                .into(binding.image)

            binding.playImageButton.setOnClickListener {
                val goToYouTube =
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.youtube.com/watch?v=" + data.key)
                    )
                itemView.context.startActivity(goToYouTube)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoTrailerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listVideo[position])
    }

    override fun getItemCount(): Int {
        return listVideo.size
    }
}