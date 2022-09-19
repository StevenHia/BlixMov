package com.stevenhia.blixmov.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stevenhia.blixmov.databinding.ItemGenreBinding
import com.stevenhia.blixmov.model.GenreData
import com.stevenhia.blixmov.ui.activity.second.SecondActivity

class GenreAdapter : RecyclerView.Adapter<GenreAdapter.ViewHolder>() {
    private var listGenre = ArrayList<GenreData>()

    fun setData(items: List<GenreData>) {
        listGenre.clear()
        listGenre.addAll(items)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GenreData) {
            binding.tvNameGenre.text = data.name

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, SecondActivity::class.java)
                intent.putExtra(SecondActivity.INTENT_GENRE_ID, data.id.toString())
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGenreBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listGenre[position])
    }

    override fun getItemCount(): Int = listGenre.size


}