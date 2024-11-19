package com.example.retrofitprov.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("DELETE FROM favorite_table WHERE prov_name = :provName")
    fun deleteByProvName(provName: String)

    @get: Query("SELECT * from favorite_table ORDER BY id ASC")
    val allFavorites: LiveData<List<Favorite>>

}