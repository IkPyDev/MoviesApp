package com.ikpydev.moviesapp.movielist.data.mapper

import com.ikpydev.moviesapp.movielist.data.local.movie.MovieEntity
import com.ikpydev.moviesapp.movielist.data.remote.respond.MovieDto
import com.ikpydev.moviesapp.movielist.domain.model.Movie

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        id = id ?: -1,
        title = title ?: "",
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        adult = adult ?: false,
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,

        category = category,

        genreIds = try {
            genreIds?.joinToString { "," } ?: "-1"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}


fun MovieEntity.toMovie(category: String): Movie {
    return Movie(
        id = id,
        title = title,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        adult = adult,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,

        category = category,
        genreIds = try {
            genreIds.split(",").map { it.toInt() }
        } catch (e:Exception){
            listOf(-1,-2)
        }
    )

}