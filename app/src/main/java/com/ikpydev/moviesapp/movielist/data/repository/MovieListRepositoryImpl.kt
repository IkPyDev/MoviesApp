package com.ikpydev.moviesapp.movielist.data.repository

import com.ikpydev.moviesapp.movielist.util.Resource
import com.ikpydev.moviesapp.movielist.data.local.movie.MovieDatabase
import com.ikpydev.moviesapp.movielist.data.mapper.toMovie
import com.ikpydev.moviesapp.movielist.data.mapper.toMovieEntity
import com.ikpydev.moviesapp.movielist.data.remote.MovieApi
import com.ikpydev.moviesapp.movielist.domain.model.Movie
import com.ikpydev.moviesapp.movielist.domain.repository.MovieListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
)  : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFormat: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localMovieList = movieDatabase.movieDao.getMovieListByCategory(category)
            val shouldLoadLocalMovie = localMovieList.isEmpty() && !forceFetchFormat

            if (shouldLoadLocalMovie){
                emit(
                    Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(category,page)
            }catch (e:IOException){
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow

            }catch (e:HttpException){
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow

            }catch (e:Exception){
                e.printStackTrace()
                emit(Resource.Error("Error loading movies"))
                return@flow

            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity(category)
                }
            }
            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(
                Resource.Success(
                movieEntities.map { it.toMovie(category) }
            ))
            emit(Resource.Loading(false))

        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovieById(id)

            if(movieEntity != null){
                Resource.Success(movieEntity.toMovie(movieEntity.category))
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such movie"))

        }
    }
}