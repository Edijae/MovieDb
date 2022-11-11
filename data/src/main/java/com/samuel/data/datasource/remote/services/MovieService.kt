package com.samuel.data.datasource.remote.services

import com.samuel.data.datasource.remote.entites.NetworkMovie
import com.samuel.data.datasource.remote.responses.ConfigurationResponse
import com.samuel.data.datasource.remote.responses.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieService {

    @GET("trending/{medial_type}/{time_window}")
    suspend fun getTrending(
        @Path("medial_type") mediaType: String,
        @Path("time_window") timeWindow: String,
    ): Response<MoviesResponse>

    @GET("discover/movie")
    suspend fun discover(@Query("page") page: Int): Response<MoviesResponse>

    @GET("configuration")
    suspend fun configuration(): Response<ConfigurationResponse>

    @GET("movie/{movie_id}")
    suspend fun movieDetails(@Path("movie_id") id: Int): Response<NetworkMovie>
}