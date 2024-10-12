package com.example.dicodingevent

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.data.response.EventResponse
import com.example.dicodingevent.data.response.ListEventsItem
import com.example.dicodingevent.data.retrofit.ApiConfig
import com.example.dicodingevent.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: EventAdapter
    private var eventsList: List<ListEventsItem> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi RecyclerView
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        adapter = EventAdapter(eventsList)
        binding.rvReview.adapter = adapter

        // Panggil API untuk mendapatkan data event
        fetchEvents()
    }

    private fun fetchEvents() {
        val client = ApiConfig.getApiService().getEvents(0) // Mengambil data dengan query active=0
        binding.progressBar.visibility = android.view.View.VISIBLE // Tampilkan progress bar
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                binding.progressBar.visibility = android.view.View.GONE // Sembunyikan progress bar
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    eventsList = eventResponse?.events ?: listOf()
                    adapter.updateData(eventsList) // Panggil method updateData untuk memperbarui adapter
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                binding.progressBar.visibility = android.view.View.GONE // Sembunyikan progress bar jika gagal
                Log.e("MainActivity", "Error: ${t.message}")
                // Contoh: Menampilkan Toast jika terjadi kegagalan
                Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
