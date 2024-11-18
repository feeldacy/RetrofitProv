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

    @Update
    fun update(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("DELETE FROM favorite_table WHERE prov_name = :provName")
    fun deleteByProvName(provName: String)

    @get: Query("SELECT * from favorite_table ORDER BY id ASC")
    val allFavorites: LiveData<List<Favorite>>

//    @Query("SELECT * FROM favorite_table")
//    fun getAllFavorites(): List<Favorite>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_table WHERE prov_name = :province)")
    fun isFavorite(province: String): Boolean

}