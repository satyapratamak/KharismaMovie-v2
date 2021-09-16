package com.satya.subm.kharismamovie.data.remote

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NowPlayingMovie(

    val id : Int,
    val overview: String?,
    val poster_path : String,
    val original_title : String,
):Parcelable{
    val baseUrl:String get() = "https://image.tmdb.org/t/p/w500"
}
