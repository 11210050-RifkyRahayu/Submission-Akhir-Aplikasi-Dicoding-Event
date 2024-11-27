package com.example.dicodingevent

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.ApiConfig
import com.example.dicodingevent.data.EventDetailResponse
import com.example.dicodingevent.data.Event
import com.example.dicodingevent.database.AppDatabase
import com.example.dicodingevent.database.FavoriteEvent
import com.example.dicodingevent.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: AppDatabase
    private var isFavorite: Boolean = false
    private var currentEvent: Event? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = AppDatabase.getDatabase(this)

        // Ambil ID event dari Intent
        val eventId = intent.getIntExtra("EXTRA_EVENT_ID", -1)
        if (eventId == -1) {
            Log.e("DetailActivity", "Invalid eventId passed to DetailActivity")
            finish() // Tutup activity jika ID tidak valid
            return
        }

        Log.d("Navigation", "Navigating to DetailActivity with eventId: $eventId")

        // Periksa status favorit dan ambil detail event
        checkFavoriteStatus(eventId)
        fetchEventDetail(eventId)

        binding.fabFavorite.setOnClickListener {
            currentEvent?.let { event ->
                if (isFavorite) {
                    removeFromFavorites(event.id)
                } else {
                    addToFavorites(event)
                }
            }
        }
    }
    private fun fetchEventDetail(eventId: Int) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetailEvent(eventId.toString())
        client.enqueue(object : Callback<EventDetailResponse> {
            override fun onResponse(call: Call<EventDetailResponse>, response: Response<EventDetailResponse>) {
                showLoading(false)
                if (response.isSuccessful) {
                    response.body()?.event?.let { event ->
                        currentEvent = event
                        updateUI(event)
                    }
                } else {
                    // Tangani error jika respons tidak berhasil
                }
            }

            override fun onFailure(call: Call<EventDetailResponse>, t: Throwable) {
                showLoading(false)
                // Tangani error jika koneksi gagal
            }
        })
    }

    private fun updateUI(event: Event) {
        Glide.with(this).load(event.mediaCover).into(binding.imageView)
        binding.tvName.text = event.name
        binding.tvOwner.text = event.ownerName
        binding.tvLocation.text = event.cityName
        binding.tvQuota.text = "${event.quota - event.registrants}"
        binding.tvCategory.text = event.category
        binding.tvDescription.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.tvBegin.text = event.beginTime

        binding.btnRegist.setOnClickListener {
            val web = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
            startActivity(web)
        }
    }

    private fun checkFavoriteStatus(eventId: Int) {
        database.favoriteEventDao().getFavoriteEventById(eventId. toString()).observe(this) { favoriteEvent ->
            isFavorite = favoriteEvent != null
            setFavoriteIcon(isFavorite)
        }
    }

    private fun addToFavorites(event: Event) {
        val favoriteEvent = FavoriteEvent(
            id = event.id,
            name = event.name,
            mediaCover = event.mediaCover,
        )

        lifecycleScope.launch {
            database.favoriteEventDao().insert(favoriteEvent)
            isFavorite = true
            setFavoriteIcon(isFavorite)
        }
    }

    private fun removeFromFavorites(eventId: Int) {
        database.favoriteEventDao().getFavoriteEventById(eventId.toString()).observe(this) { favoriteEvent ->
            favoriteEvent?.let {
                lifecycleScope.launch {
                    database.favoriteEventDao().delete(it)
                    isFavorite = false
                    setFavoriteIcon(isFavorite)
                }
            }
        }
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        if (isFavorite) {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_24)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
