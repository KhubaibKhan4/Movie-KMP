package org.moviekmp.app.data.remote

import org.moviekmp.app.data.model.Movies

interface ApiInterface {

    suspend fun popularMovieList(
        page: Int
    ): Movies

    suspend fun topRatedMovieList(
        page: Int
    ): Movies

    suspend fun upcomingMovieList(
        page: Int
    ): Movies

    suspend fun movieDetail(
        movieId: Int
    ): Movies

    suspend fun movieSearch(
        searchKey: String
    ): Movies

}