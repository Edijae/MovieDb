package com.samuel.data.datasource.remote

import com.samuel.data.datasource.remote.entites.NetworkMovie
import com.samuel.data.datasource.remote.responses.ConfigurationResponse
import com.samuel.data.datasource.remote.responses.MoviesResponse
import com.samuel.data.datasource.remote.services.MovieService
import com.samuel.data.utilities.Result
import com.samuel.data.utilities.apiCall
import retrofit2.Response
import javax.inject.Inject

// implementation for fetching movies from remote source
internal class RemoteSource @Inject constructor(private val movieService: MovieService) {

    suspend fun getConfiguration(): Result<ConfigurationResponse?> {
        val response = apiCall {
            movieService.configuration()
        }
        return response
    }

    suspend fun getMovies(page: Int): Result<MoviesResponse?> {
        val response = apiCall {
            movieService.discover(page)
        }
        return response
    }

    suspend fun getMovieDetails(id: Int): Response<NetworkMovie> {
        return movieService.movieDetails(id)
    }
}