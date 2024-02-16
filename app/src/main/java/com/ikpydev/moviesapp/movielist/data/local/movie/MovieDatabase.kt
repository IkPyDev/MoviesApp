package com.ikpydev.moviesapp.movielist.data.local.movie

import androidx.room.RoomDatabase

abstract class MovieDatabase :RoomDatabase(){
    abstract val movieDao: MovieDao
}