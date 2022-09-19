package com.stevenhia.blixmov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemCreditsBinding
import com.stevenhia.blixmov.model.Cast

class CastAdapter : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    private var listCast = ArrayList<Cast>()

    fun setData(items: List<Cast>) {
        listCast.clear()
        listCast.addAll(items)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCreditsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Cast) {
            Picasso.get().load(BuildConfig.BASE_URL_IMAGE + data.profilePath)
                .placeholder(R.drawable.ic_account)
                .into(binding.profileCredit)

            binding.titleCredit.text = data.name
            binding.tvJobCredit.text = data.character
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemCreditsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listCast[position])
    }

    override fun getItemCount(): Int = listCast.size
}