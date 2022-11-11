package com.samuel.data.repositories

import androidx.paging.PagingData
import com.samuel.data.models.Movie
import com.samuel.data.models.MovieDetails
import com.samuel.data.utilities.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    //get movies in pagination form to save memory and for increased performance
    suspend fun getMovies(): Flow<PagingData<Movie>>

    //load movie details first from room db if present. if not, load from the server
    //and save into the room db.
    suspend fun getMovieDetails(id: Int): Flow<Result<MovieDetails?>>
}