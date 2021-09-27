package com.satya.subm.kharismamovie.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.satya.subm.kharismamovie.R
import com.satya.subm.kharismamovie.data.local.FavoriteMovie
import com.satya.subm.kharismamovie.databinding.ItemNowPlayingMovieBinding


class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder>() {

    private lateinit var list:List<FavoriteMovie>

    private var onItemClickCallback : OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setFavMovieList(list : List<FavoriteMovie>){
        this.list = list
        notifyDataSetChanged()
    }

    inner class FavoriteMovieViewHolder(private val binding: ItemNowPlayingMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(favoriteMovie: FavoriteMovie){
                with(binding){
                    Glide.with(itemView)
                        .load("${favoriteMovie.baseUrl}${favoriteMovie.poster_path}")
                        .centerCrop()
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.ic_baseline_error_24)
                        .into(ivPosterPath)
                    tvOriginalTitle.text = favoriteMovie.original_title
                    binding.root.setOnClickListener{
                        onItemClickCallback?.onItemClick(favoriteMovie)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieViewHolder {
        val binding = ItemNowPlayingMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteMovieViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClick(favoriteMovie: FavoriteMovie)
    }
}