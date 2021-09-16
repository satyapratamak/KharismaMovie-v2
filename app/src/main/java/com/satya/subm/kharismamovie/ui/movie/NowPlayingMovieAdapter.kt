package com.satya.subm.kharismamovie.ui.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.data.remote.NowPlayingMovie
import com.satya.subm.kharismamovie.databinding.ItemNowPlayingMovieBinding

class NowPlayingMovieAdapter : PagingDataAdapter<NowPlayingMovie, NowPlayingMovieAdapter.NowPlayingMovieViewHolder>(
    COMPARATOR) {

    inner class NowPlayingMovieViewHolder(private val binding : ItemNowPlayingMovieBinding)
        :RecyclerView.ViewHolder(binding.root){
            fun bind(nowPlayingMovie: NowPlayingMovie){

                with(binding){
                    Glide.with(itemView)
                        .load("${nowPlayingMovie.baseUrl}${nowPlayingMovie.poster_path}")
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(ivPosterPath)
                    tvOriginalTitle.text = nowPlayingMovie.original_title

                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingMovieViewHolder {
       val binding = ItemNowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NowPlayingMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NowPlayingMovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null){
            holder.bind(currentItem)
        }
    }
    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<NowPlayingMovie> (){
            override fun areItemsTheSame(
                oldItem: NowPlayingMovie,
                newItem: NowPlayingMovie
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: NowPlayingMovie,
                newItem: NowPlayingMovie
            ): Boolean = oldItem == newItem

        }
    }


}