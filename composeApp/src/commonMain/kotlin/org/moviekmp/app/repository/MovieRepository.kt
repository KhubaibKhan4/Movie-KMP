package org.moviekmp.app.repository

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.moviekmp.app.data.model.Result
import org.moviekmp.app.data.remote.ApiImp
import org.moviekmp.app.utils.network.DataState

class MovieRepository {

    private val api = ApiImp()

    fun popularMovie(page: Int): Flow<DataState<List<Result>>> = flow {
        emit(DataState.Loading)
        try {
            val result = api.popularMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun topRatedMovie(page: Int): Flow<DataState<List<Result>>> = flow {
        emit(DataState.Loading)
        try {
            val result = api.topRatedMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun upComingMovie(page: Int): Flow<DataState<List<Result>>> = flow {
        emit(DataState.Loading)
        try {
            val result = api.upcomingMovieList(page)
            emit(DataState.Success(result.results))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun movieDetail(movieId: Int) = flow {
        emit(DataState.Loading)
        try {
            val result = api.movieDetail(movieId)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun searchMovie(searchKey: String) = flow {
        emit(DataState.Loading)
        try {
            val result = api.movieSearch(searchKey)
            emit(DataState.Success(result))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

}