package com.samuel.data.repositoryImpl

import androidx.paging.*
import com.samuel.data.datasource.local.LocalSource
import com.samuel.data.datasource.local.database.entities.asExternalModel
import com.samuel.data.datasource.local.database.entities.toExternalModel
import com.samuel.data.datasource.remote.RemoteSource
import com.samuel.data.datasource.remote.entites.asExternalModel
import com.samuel.data.models.Movie
import com.samuel.data.models.MovieDetails
import com.samuel.data.repositories.MoviesRepository
import com.samuel.data.utilities.Result
import com.samuel.data.utilities.applyEffects
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class MoviesRepositoryImpl @Inject constructor(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource
) :
    MoviesRepository {

    private var config = PagingConfig(pageSize = 15, initialLoadSize = 15)

    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config,
            remoteMediator = RemoteMediator(localSource, remoteSource)
        ) {
            localSource.getPagingSource()
        }.flow.map { pagingData ->
            pagingData.map {
                it.asExternalModel(localSource.getConfig().toExternalModel())
            }
        }
    }

    override suspend fun getMovieDetails(id: Int): Flow<Result<MovieDetails?>> = flow {
        try {
            val movie = localSource.getMovieDetails(id)
            if (movie != null) {
                emit(
                    Result.Success(
                        movie.asExternalModel(
                            localSource.getConfig().toExternalModel()
                        )
                    )
                )
            } else {
                val response = remoteSource.getMovieDetails(id)
                if (response.isSuccessful) {
                    response.body()?.let { localSource.saveMovieDetails(it) }
                    emit(
                        Result.Success(
                            response.body()
                                ?.asExternalModel(localSource.getConfig().toExternalModel())
                        )
                    )
                } else {
                    emit(Result.Failure("An error occurred. Please try again"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Result.Failure("An error occurred loading movie details. Please try again"))
        }
    }.flowOn(Dispatchers.IO).applyEffects()
}