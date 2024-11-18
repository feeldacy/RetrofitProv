package com.example.retrofitprov.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class Favorite(

    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,

    @ColumnInfo(name = "prov_name")
    val provName: String
)
