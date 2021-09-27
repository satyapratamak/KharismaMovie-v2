package com.satya.subm.kharismamovie.ui.favorite

import androidx.lifecycle.ViewModel
import com.satya.subm.kharismamovie.data.local.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(

    private val repository: FavoriteMovieRepository
): ViewModel(){

    val favoriteMovies = repository.getFavMovie()
}