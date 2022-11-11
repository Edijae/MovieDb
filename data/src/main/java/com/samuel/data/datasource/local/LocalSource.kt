package com.samuel.data.datasource.local

import NetworkImageConfig
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.room.withTransaction
import asEntity
import com.samuel.data.datasource.local.database.AppDatabase
import com.samuel.data.datasource.local.database.entities.ConfigurationEntity
import com.samuel.data.datasource.local.database.entities.MovieDetailsEntity
import com.samuel.data.datasource.local.database.entities.MovieEntity
import com.samuel.data.datasource.local.database.entities.RemoteKeyEntity
import com.samuel.data.datasource.remote.entites.NetworkMovie
import com.samuel.data.datasource.remote.entites.asDetailEntity
import com.samuel.data.datasource.remote.entites.asEntity
import javax.inject.Inject

//Load  and saves movies from room db.
internal class LocalSource @Inject constructor(private val database: AppDatabase) {

    private var imageConfig = ConfigurationEntity()

    suspend fun saveConfiguration(
        networkImageConfig: NetworkImageConfig,
        loadType: LoadType
    ) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                database.configDao().deleteById(1)
            }
            // Insert new movies into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            database.configDao().insertOrReplace(
                networkImageConfig.asEntity()
            )
        }
    }

    suspend fun saveMovies(
        networkMovies: List<NetworkMovie>,
        loadType: LoadType, page: Int
    ) {
        database.withTransaction {
            if (loadType == LoadType.REFRESH) {
                database.movieDao().clearAll()
                database.keyDao().deleteById(1)
            }
            // Insert new movies into database, which invalidates the
            // current PagingData, allowing Paging to present the updates
            // in the DB.
            database.movieDao().insertAll(networkMovies.map { it.asEntity() })
            database.keyDao().insertOrReplace(
                RemoteKeyEntity(
                    1, page,
                    page.plus(1)
                )
            )
        }
    }

    suspend fun getKey(): RemoteKeyEntity? {
        val remoteKey = database.withTransaction {
            database.keyDao().remoteKeyById(1)
        }
        return remoteKey
    }

    suspend fun getConfig(): ConfigurationEntity {
        if (imageConfig.secureBaseUrl.isEmpty()) {
            imageConfig = database.configDao().getConfigById(1)
        }
        return imageConfig
    }

    fun getPagingSource(): PagingSource<Int, MovieEntity> {
        return database.movieDao().pagingSource()
    }

    suspend fun getMovieDetails(id: Int): MovieDetailsEntity? {
        val movie = database.withTransaction {
            database.movieDetailsDao().getById(id)
        }
        return movie
    }

    suspend fun saveMovieDetails(networkMovie: NetworkMovie) {
        database.withTransaction {
            // Insert new movies into database
            database.movieDetailsDao().insertOrReplace(
                networkMovie.asDetailEntity()
            )
        }
    }
}