package com.dicoding.animax.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.animax.databinding.DetailItemAnimeBinding
import com.dicoding.core.domain.model.Episode

class DetailEpisodeAdapter(
    private val onItemClick: (Episode) -> Unit = {}
) : ListAdapter<Episode, DetailEpisodeAdapter.EpisodeViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = DetailItemAnimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private fun formatEpisode(value: String?): String {
        val number = value?.toDoubleOrNull() ?: return value ?: "-"

        return if (number % 1.0 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }

    inner class EpisodeViewHolder(
        private val binding: DetailItemAnimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(episode: Episode) {
            binding.tvEpisodeNumber.text = formatEpisode(episode.episodeNumber)
            binding.tvEpisodeName.text = episode.episodeName
            binding.tvEpisodeDate.text = episode.episodeDate
            binding.tvEpisodeId.text = episode.episodeId

            binding.root.setOnClickListener {
                onItemClick(episode)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(
                oldItem: Episode,
                newItem: Episode
            ): Boolean {
                return oldItem.episodeId == newItem.episodeId
            }

            override fun areContentsTheSame(
                oldItem: Episode,
                newItem: Episode
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}