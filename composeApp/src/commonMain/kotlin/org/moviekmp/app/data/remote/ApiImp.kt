package org.moviekmp.app.data.remote

import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.encodedPath
import org.moviekmp.app.data.model.Movies

class ApiImp : ApiInterface {


    private fun HttpRequestBuilder.popularMovie(
        page: Int
    ) {
        url {
            encodedPath = "3/movie/popular"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.topRatedMovie(
        page: Int
    ) {
        url {
            encodedPath = "3/movie/top_rated"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.upcomingMovie(
        page: Int,
    ) {
        url {
            encodedPath = "3/movie/upcoming"
            parameters.append("page", page.toString())
        }
    }

    private fun HttpRequestBuilder.movieDetail(
        movieId: Int,
    ) {
        url {
            encodedPath = "3/movie/$movieId"
        }
    }

    private fun HttpRequestBuilder.movieSearch(
        searchKey: String,
    ) {
        url {
            encodedPath = "3/search/movie"
            parameters.append("query", searchKey)
        }
    }



    override suspend fun popularMovieList(
        page: Int,
    ): Movies {
        return client.get {
            popularMovie(page)
        }.body()
    }


    override suspend fun topRatedMovieList(
        page: Int,
    ): Movies {
        return client.get {
            topRatedMovie(page)
        }.body()
    }


    override suspend fun upcomingMovieList(
        page: Int,
    ): Movies {
        return client.get {
            upcomingMovie(page)
        }.body()
    }

    override suspend fun movieDetail(movieId: Int): Movies {
        return client.get {
            movieDetail(movieId)
        }.body()
    }

    override suspend fun movieSearch(searchKey: String): Movies {
        return client.get {
            movieSearch(searchKey)
        }.body()
    }

}