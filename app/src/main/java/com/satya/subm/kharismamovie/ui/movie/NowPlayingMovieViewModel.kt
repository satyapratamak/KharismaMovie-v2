package com.satya.subm.kharismamovie.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.paging.PagingData
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovie
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovieRepository
import android.app.Application
import androidx.hilt.Assisted
import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NowPlayingMovieViewModel @Inject constructor(

    private val nowPlayingMovieRepository: NowPlayingMovieRepository,
    @Assisted state: SavedStateHandle
) :
    ViewModel() {

    companion object {

        private const val CURRENT_QUERY = "current_query"
        private const val EMPTY_QUERY = ""
    }

    private val currentQuery = state.getLiveData(CURRENT_QUERY, EMPTY_QUERY)

    //    val nowPlayingMovies: LiveData<PagingData<NowPlayingMovie>> =
//        nowPlayingMovieRepository.getNowPlayingMovie()
    val nowPlayingMovies = currentQuery.switchMap { query ->
        if (!query.isEmpty()) {

            nowPlayingMovieRepository.getSearchMovie(query)
        } else {
            nowPlayingMovieRepository.getNowPlayingMovie().cachedIn(viewModelScope)
        }

    }

    fun searchMovies(query : String){
        currentQuery.value = query
    }


}

