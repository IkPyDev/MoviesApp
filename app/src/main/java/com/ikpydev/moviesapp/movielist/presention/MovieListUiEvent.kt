package com.ikpydev.moviesapp.movielist.presention

sealed interface MovieListUiEvent{
    data class Paginate(val category:String):MovieListUiEvent
    object Native:MovieListUiEvent
}
