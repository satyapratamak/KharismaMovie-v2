package com.satya.subm.kharismamovie.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.data.local.FavoriteMovie
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovie
import com.satya.subm.kharismamovie.databinding.FragmentFavoriteMovieBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteMovieFragment : Fragment(R.layout.fragment_favorite_movie) {
    private val viewModel by viewModels<FavoriteMovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoriteMovieBinding.bind(view)

        val adapter = FavoriteMovieAdapter()

        viewModel.favoriteMovies.observe(viewLifecycleOwner) {
            adapter.setFavMovieList(it)
            binding.apply {
                rvFavoriteMovie.setHasFixedSize(true)
                rvFavoriteMovie.adapter = adapter
            }
        }

        adapter.setOnItemClickCallback(object : FavoriteMovieAdapter.OnItemClickCallback {
            override fun onItemClick(favoriteMovie: FavoriteMovie) {
                val nowPlayingMovie = NowPlayingMovie(
                    favoriteMovie.id_movie,
                    favoriteMovie.overview,
                    favoriteMovie.poster_path,
                    favoriteMovie.original_title
                )

                val action =
                    FavoriteMovieFragmentDirections.actionNavFavoriteMovieToNavDetailsMovie(
                        nowPlayingMovie
                    )
                findNavController().navigate(action)
            }

        })
    }

}