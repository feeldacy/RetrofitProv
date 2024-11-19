package com.example.retrofitprov

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.retrofitprov.database.Favorite
import com.example.retrofitprov.database.FavoriteDao
import com.example.retrofitprov.database.FavoriteRoomDatabase
import com.example.retrofitprov.databinding.ActivitySecondBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SecondActivity : AppCompatActivity() {

    private lateinit var mFavoriteDao: FavoriteDao
    private lateinit var executorService: ExecutorService
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = FavoriteRoomDatabase.getDatabase(this)
        mFavoriteDao = db!!.favoriteDao()!!

        binding.btnBack.setOnClickListener{
            onNavigateUp()
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onResume() {
        super.onResume()
        getAllFavorites()
    }

    private fun getAllFavorites(){
        mFavoriteDao.allFavorites.observe(this){
            favorites->

            val favoriteProvName = favorites.map { it.provName }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, favoriteProvName)

            binding.lvFavorites.adapter = adapter
        }
    }

}