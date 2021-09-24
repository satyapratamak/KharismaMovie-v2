package com.satya.subm.kharismamovie.ui.details

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.databinding.FragmentDetailsMovieBinding
import com.satya.subm.kharismamovie.ui.movie.NowPlayingMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailsMovieFragment : Fragment(R.layout.fragment_details_movie) {

    private val args by navArgs<DetailsMovieFragmentArgs>()
    private val detailsMovieViewModel by viewModels<DetailsMovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsMovieBinding.bind(view)
        detailsMovieViewModel
        binding.apply {
            val nowPlayingMovie = args.nowPlayingMovie

            Glide.with(this@DetailsMovieFragment)
                .load("${nowPlayingMovie.baseUrl}${nowPlayingMovie.poster_path}")
                .error(R.drawable.ic_baseline_error_24)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        tvDescription.isVisible = true
                        tvOriginalTitle.isVisible = true
                        return false
                    }

                }).into(ivPosterPath)

            tvDescription.text = nowPlayingMovie.overview
            tvOriginalTitle.text = nowPlayingMovie.original_title

            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count : Int = detailsMovieViewModel.checkMovie(nowPlayingMovie.id)

                withContext(Dispatchers.Main){
                    if (count > 0){
                        toogleFavorite.isChecked = true
                        isChecked = true
                    }else{

                        toogleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }

            toogleFavorite.setOnClickListener {
                isChecked = !isChecked

                if (isChecked){
                    detailsMovieViewModel.addToFavorite(nowPlayingMovie)
                }else{
                    detailsMovieViewModel.removeFromFavorite(nowPlayingMovie.id)
                }

                toogleFavorite.isChecked = isChecked
            }


        }


    }
    /*
    private val detailsMovieViewModel by viewModels<DetailsMovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailsMovieBinding.bind(view)

        binding.apply{
            val nowPlayingMovie = args.nowPlayingMovie
            Glide.with(this@DetailsMovieFragment)
                 .load("${nowPlayingMovie.baseUrl}${nowPlayingMovie.poster_path}")
                 .error(R.drawable.ic_baseline_error_24)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.isVisible = false
                        tvDescription.isVisible = true
                        tvOriginalTitle.isVisible = true
                        return false
                    }

                }).into(ivPosterPath)






            var isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count : Int = detailsMovieViewModel.checkMovie(nowPlayingMovie.id)

                withContext(Dispatchers.Main){
                    if (count > 0){
                        toogleFavorite.isChecked = true
                        isChecked = true
                    }else{

                        toogleFavorite.isChecked = false
                        isChecked = false
                    }
                }
            }


        }
    }*/


}