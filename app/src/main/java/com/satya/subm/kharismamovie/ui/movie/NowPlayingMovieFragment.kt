package com.satya.subm.kharismamovie.ui.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.databinding.FragmentNowPlayingMovieBinding
import com.satya.subm.kharismamovie.databinding.NowPlayingMovieLoadStateFooterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_now_playing_movie.*
import kotlinx.android.synthetic.main.now_playing_movie_load_state_footer.*


@AndroidEntryPoint
class NowPlayingMovieFragment : Fragment(R.layout.fragment_now_playing_movie) {
    private val nowPlayingMovieViewModel by viewModels<NowPlayingMovieViewModel>()
    private var _binding: FragmentNowPlayingMovieBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNowPlayingMovieBinding.bind(view)

        val nowPlayingMovieAdapter = NowPlayingMovieAdapter()

        binding.apply {
            rvNowPlayingMovie.setHasFixedSize(true)
            rvNowPlayingMovie.adapter = nowPlayingMovieAdapter.withLoadStateHeaderAndFooter(
                header = NowPlayingMovieLoadStateAdapter { nowPlayingMovieAdapter.retry() },
                footer = NowPlayingMovieLoadStateAdapter { nowPlayingMovieAdapter.retry() }

            )
            itemNowPlayingMovieLoadStateFooter.btnRetry.setOnClickListener {
                nowPlayingMovieAdapter.retry()
            }


        }

        nowPlayingMovieAdapter.addLoadStateListener { loadState ->
            binding.apply {
                progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                rvNowPlayingMovie.isVisible = loadState.source.refresh is LoadState.NotLoading
                //btn_retry.isVisible = loadState.source.refresh is LoadState.Error
                itemNowPlayingMovieLoadStateFooter.btnRetry.isVisible = loadState.source.refresh is LoadState.Error
                itemNowPlayingMovieLoadStateFooter.tvError.isVisible = loadState.source.refresh is LoadState.Error
                itemNowPlayingMovieLoadStateFooter.progressBar.isVisible = loadState.source.refresh is LoadState.Error
                tvFailed.isVisible = loadState.source.refresh is LoadState.Error

                ///not found
                if(loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && nowPlayingMovieAdapter.itemCount < 1){
                    rvNowPlayingMovie.isVisible = false
                    tvDataNotFound.isVisible = true
                }else{
                    tvDataNotFound.isVisible = false
                }
            }

        }
        nowPlayingMovieViewModel.nowPlayingMovies.observe(viewLifecycleOwner) {
            nowPlayingMovieAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!= null){
                    binding.rvNowPlayingMovie.scrollToPosition(0)
                    nowPlayingMovieViewModel.searchMovies(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })
    }
}