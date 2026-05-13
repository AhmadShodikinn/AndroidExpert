package com.dicoding.animax.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.animax.core.databinding.HomeItemAnimeBinding
import com.dicoding.core.domain.model.ScheduleAnimeItem

class ScheduleAnimeAdapter(
    private val onItemClick: (ScheduleAnimeItem) -> Unit
) : ListAdapter<ScheduleAnimeItem, ScheduleAnimeAdapter.ScheduleAnimeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleAnimeViewHolder {
        val binding = HomeItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ScheduleAnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleAnimeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ScheduleAnimeViewHolder(
        private val binding: HomeItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: ScheduleAnimeItem) {
            binding.root.setOnClickListener {
                onItemClick(anime)
            }

            binding.tvAnimeTitle.text = anime.title

            Glide.with(binding.root.context)
                .load(anime.poster)
                .into(binding.imgPoster)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ScheduleAnimeItem>() {
            override fun areItemsTheSame(
                oldItem: ScheduleAnimeItem,
                newItem: ScheduleAnimeItem
            ): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            override fun areContentsTheSame(
                oldItem: ScheduleAnimeItem,
                newItem: ScheduleAnimeItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}