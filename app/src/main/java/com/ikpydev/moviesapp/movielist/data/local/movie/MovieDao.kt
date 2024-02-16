package com.ikpydev.moviesapp.movielist.data.local.movie

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList:List<MovieEntity>)

    @Query("SELECT * FROM MovieEntitny WHERE id = :id")
    suspend fun getMovieById(id:Int): MovieEntity

    @Query("SELECT * FROM MovieEntitny WHERE category = :category")
    suspend fun getMovieListByCategory(category:String):List<MovieEntity>
}