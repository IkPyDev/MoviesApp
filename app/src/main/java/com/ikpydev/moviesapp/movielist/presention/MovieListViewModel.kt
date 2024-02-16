package com.ikpydev.moviesapp.movielist.presention

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ikpydev.moviesapp.movielist.domain.repository.MovieListRepository
import com.ikpydev.moviesapp.movielist.util.Category
import com.ikpydev.moviesapp.movielist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    private var _movieListState = MutableStateFlow(MovieListState())
    val movieListState = _movieListState.asStateFlow()


    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Native -> {
                _movieListState.update {
                    it.copy(isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen)
                }
            }

            is MovieListUiEvent.Paginate -> {

                if (event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }

            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFormat: Boolean) {

        viewModelScope.launch(Dispatchers.IO) {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchFormat,
                Category.UPCOMING,
                movieListState.value.upcomingMoviesListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { upcomingList ->
                            _movieListState.update {
                                it.copy(
                                    upcomingMoviesList = movieListState.value.upcomingMoviesList
                                            + upcomingList.shuffled(),
                                    upcomingMoviesListPage = movieListState.value.upcomingMoviesListPage
                                )
                            }

                        }

                    }
                }
            }
        }

    }

    private fun getPopularMovieList(forceFetchFormat: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieListState.update {
                it.copy(isLoading = true)
            }
            movieListRepository.getMovieList(
                forceFetchFormat,
                Category.POPULAR,
                movieListState.value.popularMoviesListPage
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = true)
                        }
                    }

                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }

                    is Resource.Success -> {
                        result.data?.let { popularList ->
                            _movieListState.update {
                                it.copy(
                                    popularMoviesList = movieListState.value.popularMoviesList
                                            + popularList.shuffled(),
                                    popularMoviesListPage = movieListState.value.popularMoviesListPage
                                )
                            }

                        }

                    }
                }
            }
        }
    }

}