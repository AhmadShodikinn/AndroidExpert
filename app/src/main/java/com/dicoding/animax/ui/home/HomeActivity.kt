package com.dicoding.animax.ui.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.animax.AnimaxApplication
import com.dicoding.animax.R
import com.dicoding.animax.databinding.HomeLayoutBinding
import com.dicoding.animax.di.ViewModelFactory
import com.dicoding.animax.ui.detail.DetailAnimeActivity
import com.dicoding.animax.ui.schedule.ScheduleActivity
import com.dicoding.core.domain.model.Anime
import com.dicoding.core.ui.HomeAnimeAdapter
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: HomeLayoutBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAnimeAdapter: HomeAnimeAdapter

    private var originalAnimeList = listOf<Anime>()

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AnimaxApplication).appComponent.inject(this)

        binding = HomeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        setupRecyclerView()
        setupSearch()
        setupMenuButton()
        observeAnime()
    }

    private fun setupMenuButton() {
        binding.btnFavorite.setOnClickListener {
            val intent = Intent()
            intent.setClassName(
                packageName,
                "com.dicoding.favorite.FavoriteActivity"
            )
            startActivity(intent)
        }

        binding.btnSchedule.setOnClickListener {
            val intent = Intent(this, ScheduleActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        homeAnimeAdapter = HomeAnimeAdapter { anime ->
            val intent = Intent(this, DetailAnimeActivity::class.java)
            intent.putExtra(DetailAnimeActivity.EXTRA_ANIME_ID, anime.animeId)
            startActivity(intent)
        }

        binding.rvAnime.apply {
            layoutManager = LinearLayoutManager(this@HomeActivity)
            adapter = homeAnimeAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeAnime() {
        showLoading(true)

        homeViewModel.anime.observe(this) { result ->
            showLoading(false)

            result.onSuccess { animeList ->
                originalAnimeList = animeList
                submitAnimeList(animeList)
            }

            result.onFailure { throwable ->
                showError(throwable.message ?: resources.getString(R.string.alert_failure))
            }
        }
    }

    private fun setupSearch() {
        binding.edtSearchAnime.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                val keyword = s.toString()

                val filteredList = if (keyword.isBlank()) {
                    originalAnimeList
                } else {
                    originalAnimeList.filter { anime ->
                        anime.title.contains(keyword, ignoreCase = true)
                    }
                }

                submitAnimeList(filteredList)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun submitAnimeList(animeList: List<Anime>) {
        homeAnimeAdapter.submitList(animeList)

        if (animeList.isEmpty()) {
            binding.tvEmptyHome.text = resources.getString(R.string.alert_notfound)
            binding.tvEmptyHome.visibility = View.VISIBLE
            binding.rvAnime.visibility = View.GONE
        } else {
            binding.tvEmptyHome.visibility = View.GONE
            binding.rvAnime.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarHome.visibility = if (isLoading) View.VISIBLE else View.GONE

        if (isLoading) {
            binding.rvAnime.visibility = View.GONE
            binding.tvEmptyHome.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.tvEmptyHome.text = message
        binding.tvEmptyHome.visibility = View.VISIBLE
        binding.rvAnime.visibility = View.GONE
    }
}