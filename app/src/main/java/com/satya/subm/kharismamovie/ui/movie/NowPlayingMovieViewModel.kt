package com.satya.subm.kharismamovie.ui.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovie
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovieRepository
import android.app.Application
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NowPlayingMovieViewModel @Inject constructor(private val nowPlayingMovieRepository: NowPlayingMovieRepository) :
    ViewModel() {
    val nowPlayingMovies: LiveData<PagingData<NowPlayingMovie>> =
        nowPlayingMovieRepository.getNowPlayingMovie()


}

