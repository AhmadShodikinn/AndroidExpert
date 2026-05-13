package com.dicoding.animax.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.animax.AnimaxApplication
import com.dicoding.animax.R
import com.dicoding.animax.databinding.DetailAnimeLayoutBinding
import com.dicoding.animax.di.ViewModelFactory
import com.dicoding.core.domain.model.Anime
import javax.inject.Inject

class DetailAnimeActivity : AppCompatActivity() {

    private lateinit var binding: DetailAnimeLayoutBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var detailEpisodeAdapter: DetailEpisodeAdapter

    private var currentAnime: Anime? = null
    private var isFavorite = false

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AnimaxApplication).appComponent.inject(this)

        binding = DetailAnimeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detailViewModel = ViewModelProvider(
            this,
            factory
        )[DetailViewModel::class.java]

        binding.btnBackDetail.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        setupRecyclerView()

        val animeId = intent.getStringExtra(EXTRA_ANIME_ID)

        if (animeId.isNullOrEmpty()) {
            showError(resources.getString(R.string.alert_detail_notfound))
            return
        }

        observeFavoriteStatus(animeId)
        setupFavoriteButton()
        observeDetailAnime(animeId)
    }

    private fun setupRecyclerView() {
        detailEpisodeAdapter = DetailEpisodeAdapter() { episode ->
            Toast.makeText(
                this,
                "Episode: ${episode.episodeName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvEpisode.apply {
            layoutManager = LinearLayoutManager(this@DetailAnimeActivity)
            adapter = detailEpisodeAdapter
            isNestedScrollingEnabled = false
        }
    }

    private fun observeFavoriteStatus(animeId: String) {
        detailViewModel.isFavoriteAnime(animeId).observe(this) { favorite ->
            isFavorite = favorite
            setFavoriteButtonState(favorite)
        }
    }

    private fun setupFavoriteButton() {
        binding.btnFavoriteDetail.setOnClickListener {
            val anime = currentAnime

            if (anime == null) {
                Toast.makeText(
                    this,
                    resources.getString(R.string.alert_notfound),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (isFavorite) {
                detailViewModel.deleteFavoriteAnime(anime.animeId)
                Toast.makeText(
                    this,
                    resources.getString(R.string.alert_delete_fav),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                detailViewModel.insertFavoriteAnime(anime)
                Toast.makeText(
                    this,
                    resources.getString(R.string.alert_insert_fav),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // helper double to int (1031.0 => 1030)
    private fun formatEpisode(value: String?): String {
        val number = value?.toDoubleOrNull() ?: return value ?: "-"
        return if (number % 1.0 == 0.0) {
            number.toInt().toString()
        } else {
            number.toString()
        }
    }

    private fun observeDetailAnime(animeId: String) {
        showLoading(true)

        detailViewModel.getDetailAnime(animeId).observe(this) { result ->
            showLoading(false)

            result.onSuccess { detailAnime ->
                currentAnime = Anime(
                    animeId = animeId,
                    title = detailAnime.title,
                    poster = detailAnime.poster,
                    episodes = detailAnime.episodeList.size,
                    releaseDay = detailAnime.status,
                    releaseDate = detailAnime.aired,
                    score = detailAnime.score,
                    href = "",
                    otakudesuUrl = "",
                    type = detailAnime.type,
                    isFavorite = isFavorite
                )

                binding.tvDetailTitle.text = detailAnime.title
                binding.tvDetailJapanese.text = detailAnime.japanese
                binding.tvDetailScore.text = getString(R.string.detail_score, detailAnime.score)
                binding.tvDetailStatus.text = getString(R.string.detail_status, detailAnime.status)
                binding.tvDetailType.text = detailAnime.type
                binding.tvDetailEpisodes.text = getString(
                    R.string.detail_episode,
                    formatEpisode(detailAnime.episodes)
                )
                binding.tvDetailType.text = detailAnime.type
                binding.tvDetailDuration.text = detailAnime.duration
                binding.tvDetailAired.text = detailAnime.aired
                binding.tvDetailStudios.text = detailAnime.studios
                binding.tvDetailProducers.text = detailAnime.producers
                binding.tvDetailGenre.text = detailAnime.genres.joinToString(", ")
                binding.tvDetailSynopsis.text = detailAnime.synopsis

                Glide.with(this)
                    .load(detailAnime.poster)
                    .into(binding.imgDetailPoster)

                detailEpisodeAdapter.submitList(detailAnime.episodeList)
            }

            result.onFailure { throwable ->
                showError(throwable.message ?: resources.getString(R.string.alert_failure))
            }
        }
    }

    private fun setFavoriteButtonState(isFavorite: Boolean) {
        binding.btnFavoriteDetail.setImageResource(
            if (isFavorite) R.drawable.favorite_filled_24px
            else R.drawable.favorite_24px
        )

        binding.btnFavoriteDetail.imageTintList = ContextCompat.getColorStateList(
            this,
            if (isFavorite) android.R.color.holo_red_dark
            else android.R.color.darker_gray
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarDetail.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showError(message: String) {
        binding.progressBarDetail.visibility = View.GONE
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_ANIME_ID = "extra_anime_id"
    }
}