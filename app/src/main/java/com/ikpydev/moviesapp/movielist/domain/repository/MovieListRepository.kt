package com.ikpydev.moviesapp.movielist.domain.repository

import com.ikpydev.moviesapp.movielist.util.Resource
import com.ikpydev.moviesapp.movielist.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        forceFetchFormat:Boolean,
        category:String,
        page:Int
    ):Flow<Resource<List<Movie>>>

    suspend fun getMovie(id:Int):Flow<Resource<Movie>>
}