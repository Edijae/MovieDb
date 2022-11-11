package com.samuel.movie.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.samuel.data.models.Movie
import com.samuel.data.models.MovieDetails
import com.samuel.data.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    val state = MutableStateFlow<com.samuel.data.utilities.Result<MovieDetails?>>(
        com.samuel.data.utilities.Result.Progress(true)
    )

    //Caches the PagingData such that any downstream collection from this flow will share the same PagingData.
    suspend fun getMovies(): Flow<PagingData<Movie>> {
        return repository.getMovies()
            .cachedIn(viewModelScope)
    }

    //fetch movie details from the given id
    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            repository.getMovieDetails(id).collectLatest {
                state.value = it
            }
        }
    }

}