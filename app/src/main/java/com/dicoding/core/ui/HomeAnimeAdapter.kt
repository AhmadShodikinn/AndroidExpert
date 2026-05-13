package com.dicoding.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.animax.core.databinding.HomeItemAnimeBinding
import com.dicoding.core.domain.model.Anime

class HomeAnimeAdapter(
    private val onItemClick: (Anime) -> Unit
) : ListAdapter<Anime, HomeAnimeAdapter.HomeAnimeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAnimeViewHolder {
        val binding = HomeItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeAnimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeAnimeViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }

    class HomeAnimeViewHolder(
        private val binding: HomeItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            anime: Anime,
            onItemClick: (Anime) -> Unit
        ) {
            binding.tvAnimeTitle.text = anime.title
            binding.tvAnimeReleaseDay.text = anime.releaseDay
            binding.tvAnimeEpisode.text = anime.episodes.toString()

            Glide.with(binding.root.context)
                .load(anime.poster)
                .into(binding.imgPoster)

            binding.root.setOnClickListener {
                onItemClick(anime)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Anime>() {
            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem.animeId == newItem.animeId
            }

            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem == newItem
            }
        }
    }
}