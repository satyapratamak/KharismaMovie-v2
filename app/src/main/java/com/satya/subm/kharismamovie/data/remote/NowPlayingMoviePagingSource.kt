package com.satya.subm.kharismamovie.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1

class NowPlayingMoviePagingSource(
    private val nowPlayingMovieAPI: NowPlayingMovieAPI,
    private val query : String?
    ) :
    PagingSource<Int, NowPlayingMovie>() {
    override fun getRefreshKey(state: PagingState<Int, NowPlayingMovie>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingMovie> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = if (query != null) nowPlayingMovieAPI.getSearchMovie(query, position) else nowPlayingMovieAPI.getNowPlayingMovies(position)


            val nowPlayingMovies = response.results

            LoadResult.Page(
                data = nowPlayingMovies,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (nowPlayingMovies.isEmpty()) null else position + 1

            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

}