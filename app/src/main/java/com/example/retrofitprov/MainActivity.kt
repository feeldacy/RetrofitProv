package com.example.retrofitprov

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitprov.database.Favorite
import com.example.retrofitprov.database.FavoriteDao
import com.example.retrofitprov.database.FavoriteRoomDatabase
import com.example.retrofitprov.databinding.ActivityMainBinding
import com.example.retrofitprov.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mFavoriteDao: FavoriteDao
    private lateinit var executorService: ExecutorService
    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        executorService = Executors.newSingleThreadExecutor()
        val db = FavoriteRoomDatabase.getDatabase(this)
        mFavoriteDao = db!!.favoriteDao()!!

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProv.layoutManager = LinearLayoutManager(this)

        val client = ApiClient.getInstance()
        val response = client.getProvinsi()

        response.enqueue(object : Callback<Map<String, String>> {
            override fun onResponse(call: Call<Map<String, String>>, response: Response<Map<String, String>>) {
                if (response.isSuccessful && response.body() != null) {
                    val provMap = response.body()!!
                    val provList = provMap.values.toList()

                    mFavoriteDao.allFavorites.observe(this@MainActivity) { favoriteList ->
                        val favoriteProvinces = favoriteList.map { it.provName }.toSet()

                        val adapter = ProvAdapter(provList, favoriteProvinces) { province, isBookmarked ->
                            handleBookmark(province, isBookmarked)
                        }

                        binding.rvProv.adapter = adapter
                    }

                } else {
                    Toast.makeText(this@MainActivity, "Gagal mengambil data provinsi", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Map<String, String>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Koneksi error", Toast.LENGTH_LONG).show()
            }
        })

        binding.btnFavorites.setOnClickListener{
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun handleBookmark(province: String, isBookmarked: Boolean) {
        executorService.execute {
            val isFavorite = mFavoriteDao.isFavorite(province)
            if (isBookmarked) {
                if (isFavorite) {
                    runOnUiThread {
                        Toast.makeText(this, "Province already in favorites", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    mFavoriteDao.insert(Favorite(provName = province))
                }
            } else {
                mFavoriteDao.deleteByProvName(province)
            }

        }
    }
}