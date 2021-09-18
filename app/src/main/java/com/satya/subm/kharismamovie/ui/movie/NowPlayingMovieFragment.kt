package com.satya.subm.kharismamovie.ui.movie

import android.os.Bundle
import android.view.View
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
    }
}