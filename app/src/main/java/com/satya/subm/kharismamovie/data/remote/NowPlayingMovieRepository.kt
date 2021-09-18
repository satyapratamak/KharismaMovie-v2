package com.satya.subm.kharismamovie.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NowPlayingMovieRepository @Inject constructor(private val nowPlayingMovieAPI: NowPlayingMovieAPI) {

    fun getNowPlayingMovie() =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NowPlayingMoviePagingSource(nowPlayingMovieAPI) }
        ).liveData
}