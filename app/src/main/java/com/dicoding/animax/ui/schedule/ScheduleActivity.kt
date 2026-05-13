package com.dicoding.animax.ui.schedule

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.animax.AnimaxApplication
import com.dicoding.animax.R
import com.dicoding.animax.databinding.ScheduleLayoutBinding
import com.dicoding.animax.di.ViewModelFactory
import com.dicoding.animax.ui.detail.DetailAnimeActivity
import com.dicoding.core.domain.model.ScheduleAnime
import javax.inject.Inject

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ScheduleLayoutBinding
    private lateinit var scheduleViewModel: ScheduleViewModel
    private lateinit var scheduleAnimeAdapter: ScheduleAnimeAdapter

    private var originalScheduleList = listOf<ScheduleAnime>()
    private var selectedDay = DAY_ALL

    @Inject
    lateinit var factory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (application as AnimaxApplication).appComponent.inject(this)

        binding = ScheduleLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackSchedule.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        scheduleViewModel = ViewModelProvider(this, factory)[ScheduleViewModel::class.java]

        setupRecyclerView()
        setupDayFilter()
        observeScheduleAnime()
    }

    private fun setupRecyclerView() {
        scheduleAnimeAdapter = ScheduleAnimeAdapter { anime ->
            val intent = Intent(this, DetailAnimeActivity::class.java)
            intent.putExtra(DetailAnimeActivity.EXTRA_ANIME_ID, anime.animeId)
            startActivity(intent)
        }

        binding.rvScheduleAnime.apply {
            layoutManager = LinearLayoutManager(this@ScheduleActivity)
            adapter = scheduleAnimeAdapter
            setHasFixedSize(true)
        }
    }

    private fun observeScheduleAnime() {
        showLoading(true)

        scheduleViewModel.scheduleAnime.observe(this) { result ->
            showLoading(false)

            result.onSuccess { scheduleList ->
                originalScheduleList = scheduleList
                submitScheduleByDay(selectedDay)
            }

            result.onFailure { throwable ->
                showError(throwable.message ?: resources.getString(R.string.alert_failure))
            }
        }
    }

    private fun setupDayFilter() {
        val dayChips = mapOf(
            DAY_ALL to binding.tvDayAll,
            "senin" to binding.tvDayMonday,
            "selasa" to binding.tvDayTuesday,
            "rabu" to binding.tvDayWednesday,
            "kamis" to binding.tvDayThursday,
            "jumat" to binding.tvDayFriday,
            "sabtu" to binding.tvDaySaturday,
            "minggu" to binding.tvDaySunday
        )

        dayChips.forEach { (day, textView) ->
            textView.setOnClickListener {
                selectedDay = day
                updateSelectedChip(dayChips, selectedDay)
                submitScheduleByDay(selectedDay)
            }
        }

        updateSelectedChip(dayChips, selectedDay)
    }

    private fun submitScheduleByDay(day: String) {
        val animeList = if (day == DAY_ALL) {
            originalScheduleList.flatMap { schedule ->
                schedule.animeList
            }
        } else {
            originalScheduleList
                .filter { schedule ->
                    schedule.day.equals(day, ignoreCase = true)
                }
                .flatMap { schedule ->
                    schedule.animeList
                }
        }

        binding.tvScheduleSectionTitle.text = if (day == DAY_ALL) {
            "Semua Jadwal"
        } else {
            "Jadwal ${day.replaceFirstChar { it.uppercase() }}"
        }

        scheduleAnimeAdapter.submitList(animeList)

        if (animeList.isEmpty()) {
            binding.tvEmptySchedule.text = "Jadwal anime belum tersedia"
            binding.tvEmptySchedule.visibility = View.VISIBLE
            binding.rvScheduleAnime.visibility = View.GONE
        } else {
            binding.tvEmptySchedule.visibility = View.GONE
            binding.rvScheduleAnime.visibility = View.VISIBLE
        }
    }

    private fun updateSelectedChip(
        dayChips: Map<String, TextView>,
        selectedDay: String
    ) {
        dayChips.forEach { (day, textView) ->
            val isSelected = day == selectedDay

            textView.background = ContextCompat.getDrawable(
                this,
                if (isSelected) R.drawable.bg_chip_selected else R.drawable.bg_chip
            )

            textView.setTextColor(
                ContextCompat.getColor(
                    this,
                    if (isSelected) android.R.color.white else android.R.color.darker_gray
                )
            )

            textView.setTypeface(
                null,
                if (isSelected) Typeface.BOLD else Typeface.NORMAL
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarSchedule.visibility = if (isLoading) View.VISIBLE else View.GONE

        if (isLoading) {
            binding.rvScheduleAnime.visibility = View.GONE
            binding.tvEmptySchedule.visibility = View.GONE
        }
    }

    private fun showError(message: String) {
        binding.tvEmptySchedule.text = message
        binding.tvEmptySchedule.visibility = View.VISIBLE
        binding.rvScheduleAnime.visibility = View.GONE
    }

    companion object {
        private const val DAY_ALL = "semua"
    }
}