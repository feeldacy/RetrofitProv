package com.example.retrofitprov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase



@Database(entities = [Favorite::class], version = 1, exportSchema = false)

abstract class FavoriteRoomDatabase : RoomDatabase(){
    abstract fun favoriteDao() : FavoriteDao?

    companion object {
        @Volatile
        private var INSTANCE: FavoriteRoomDatabase? = null

        fun getDatabase(context: Context) : FavoriteRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteRoomDatabase::class.java){
                    INSTANCE = databaseBuilder(context.applicationContext,
                        FavoriteRoomDatabase::class.java, "favorite_database").build()
                }
            }
            return INSTANCE
        }
    }

}