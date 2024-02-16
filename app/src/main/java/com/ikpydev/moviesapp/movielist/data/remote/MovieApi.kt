package com.ikpydev.moviesapp.movielist.data.remote

import com.ikpydev.moviesapp.movielist.data.remote.respond.MovieDto
import com.ikpydev.moviesapp.movielist.data.remote.respond.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieApi {

    @GET("movie/{category}")
    suspend fun getMoviesList(
        @Path("category") category:String,
        @Query("page") page:Int,
        @Query("api") api:String = API_KEY
    ): MovieListDto



    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
        const val API_KEY = "xxxx"
    }
}