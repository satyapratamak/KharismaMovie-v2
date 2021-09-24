package com.satya.subm.kharismamovie.ui.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.satya.subm.kharismamovie.data.local.FavoriteMovie
import com.satya.subm.kharismamovie.data.local.FavoriteMovieRepository
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsMovieViewModel @Inject constructor(

    private val repository: FavoriteMovieRepository
): ViewModel(){
    fun addToFavorite(nowPlayingMovie: NowPlayingMovie){

        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    nowPlayingMovie.id,
                    nowPlayingMovie.original_title,
                    nowPlayingMovie.overview,
                    nowPlayingMovie.poster_path
                )
            )
        }
    }

    suspend fun checkMovie(id:String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }


}
/*
@HiltViewModel
class DetailsMovieViewModel @Inject constructor(
    private val repository: FavoriteMovieRepository
) : ViewModel() {

    fun addToFavorite(nowPlayingMovie: NowPlayingMovie){
        CoroutineScope(Dispatchers.IO).launch {
            repository.addToFavorite(
                FavoriteMovie(
                    nowPlayingMovie.id,
                    nowPlayingMovie.overview,
                    nowPlayingMovie.poster_path,
                    nowPlayingMovie.original_title,


                )
            )
        }
    }

    suspend fun checkMovie(id:String) = repository.checkMovie(id)

    fun removeFromFavorite(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            repository.removeFromFavorite(id)
        }
    }

}*/