package com.dicoding.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.animax.AnimaxApplication
import com.dicoding.core.ui.HomeAnimeAdapter
import com.dicoding.favorite.databinding.FavoriteLayoutBinding
import com.dicoding.favorite.di.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: FavoriteLayoutBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favoriteAdapter: HomeAnimeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FavoriteLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appComponent = (application as AnimaxApplication).appComponent
        val factory = FavoriteViewModelFactory(appComponent.animeUseCase())

        favoriteViewModel = ViewModelProvider(
            this,
            factory
        )[FavoriteViewModel::class.java]

        setupRecyclerView()
        setupAction()
        observeFavoriteAnime()
    }

    private fun setupRecyclerView() {
        favoriteAdapter = HomeAnimeAdapter { anime ->
            val intent = Intent()
            intent.setClassName(
                packageName,
                DETAIL_ACTIVITY_CLASS_NAME
            )
            intent.putExtra(EXTRA_ANIME_ID, anime.animeId)
            startActivity(intent)
        }

        binding.rvFavoriteAnime.apply {
            layoutManager = LinearLayoutManager(this@FavoriteActivity)
            adapter = favoriteAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupAction() {
        binding.btnBackFavorite.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun observeFavoriteAnime() {
        favoriteViewModel.getFavoriteAnime().observe(this) { animeList ->
            Log.d("FavoritePage", "Total favorite: ${animeList.size}")

            favoriteAdapter.submitList(animeList)

            val isEmpty = animeList.isEmpty()

            binding.tvEmptyFavorite.visibility =
                if (isEmpty) View.VISIBLE else View.GONE

            binding.rvFavoriteAnime.visibility =
                if (isEmpty) View.GONE else View.VISIBLE
        }
    }

    companion object {
        private const val DETAIL_ACTIVITY_CLASS_NAME =
            "com.dicoding.animax.ui.detail.DetailAnimeActivity"

        private const val EXTRA_ANIME_ID = "extra_anime_id"
    }
}