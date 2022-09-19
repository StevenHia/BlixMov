package com.stevenhia.blixmov.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.stevenhia.blixmov.BuildConfig
import com.stevenhia.blixmov.R
import com.stevenhia.blixmov.databinding.ItemCreditsBinding
import com.stevenhia.blixmov.model.Crew

class CrewAdapter : RecyclerView.Adapter<CrewAdapter.ViewHolder>() {
    private val listCrew = ArrayList<Crew>()

    fun setData(items: List<Crew>) {
        listCrew.clear()
        listCrew.addAll(items)
        this.notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemCreditsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Crew) {
            Picasso.get().load(BuildConfig.BASE_URL_IMAGE + data.profilePath)
                .placeholder(R.drawable.ic_account)
                .into(binding.profileCredit)

            binding.titleCredit.text = data.name
            binding.tvJobCredit.text = data.department
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
        holder.bind(listCrew[position])
    }

    override fun getItemCount(): Int = listCrew.size
}