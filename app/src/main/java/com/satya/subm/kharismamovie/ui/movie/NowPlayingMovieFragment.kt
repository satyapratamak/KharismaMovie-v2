package com.satya.subm.kharismamovie.ui.movie

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.databinding.FragmentNowPlayingMovieBinding
import dagger.hilt.android.AndroidEntryPoint


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