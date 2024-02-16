package com.ikpydev.moviesapp.movielist.data.local.movie

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class MovieEntity (
    @PrimaryKey
    val id: Int,
    val category:String,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)