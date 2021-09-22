package com.satya.subm.kharismamovie.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite_movie")
data class FavoriteMovie (
    val id_movie: String,
    val original_title: String,
    val overview: String?,
    val poster_path: String,

):Serializable{

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}