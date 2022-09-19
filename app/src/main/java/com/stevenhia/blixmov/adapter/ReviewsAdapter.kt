package com.stevenhia.blixmov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemReviewBinding
import com.stevenhia.blixmov.model.ReviewData

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ViewHolder>() {
    private val listReview = ArrayList<ReviewData>()

    fun setData(items: List<ReviewData>) {
        listReview.clear()
        listReview.addAll(items)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewData) {
            binding.tvNameUserReview.text =
                itemView.context.getString(R.string.getAuthor, data.author)
            binding.tvContentReview.text = data.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemReviewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listReview[position])
    }

    override fun getItemCount(): Int = listReview.size
}