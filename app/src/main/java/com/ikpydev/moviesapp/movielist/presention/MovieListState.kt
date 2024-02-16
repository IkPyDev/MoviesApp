package com.ikpydev.moviesapp.movielist.presention

import com.ikpydev.moviesapp.movielist.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val popularMoviesListPage:Int = 1,
    val upcomingMoviesListPage:Int = 1,

    val isCurrentPopularScreen:Boolean = true,

    val popularMoviesList: List<Movie> = emptyList(),
    val upcomingMoviesList: List<Movie> = emptyList()
)
