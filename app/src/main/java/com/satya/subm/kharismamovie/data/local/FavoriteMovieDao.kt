package com.satya.subm.kharismamovie.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteMovieDao {

    @Insert
    suspend fun addToFavorite(favoriteMovie: FavoriteMovie)

    @Query("SELECT count(*) FROM favorite_movie WHERE favorite_movie.id_movie = :id")
    suspend fun checkMovie(id:String) : Int

    @Query("DELETE FROM favorite_movie WHERE favorite_movie.id_movie = :id")
    suspend fun removeFromFavorite(id:String) : Int

}