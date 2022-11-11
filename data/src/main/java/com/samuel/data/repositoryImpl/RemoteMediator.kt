package com.samuel.data.repositoryImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.samuel.data.datasource.local.LocalSource
import com.samuel.data.datasource.local.database.entities.MovieEntity
import com.samuel.data.datasource.remote.RemoteSource

@OptIn(ExperimentalPagingApi::class)
internal class RemoteMediator(
    private val localSource: LocalSource,
    private val remoteSource: RemoteSource,
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun initialize(): InitializeAction {
        val currentPage = localSource.getKey()?.currentPage ?: 0
        return if (currentPage == 0) {
            // Need to refresh cached data from network; returning
            // LAUNCH_INITIAL_REFRESH here will also block RemoteMediator's
            // APPEND and PREPEND from running until REFRESH succeeds.
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            // Cached data is up-to-date, so there is no need to re-fetch
            // from the network.
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        // The network load method takes an optional after=<movie.id>
        // parameter. For every page after the first, pass the last movie
        // ID to let it continue from where it left off. For REFRESH,
        // pass null to load the first page.
        val loadKey = when (loadType) {
            LoadType.REFRESH -> {
                null
            }
            // you never need to prepend, since REFRESH
            // will always load the first page in the list. Immediately
            // return, reporting end of pagination.
            LoadType.PREPEND -> {
                return MediatorResult.Success(
                    endOfPaginationReached = true
                )
            }
            LoadType.APPEND -> {
                val key = localSource.getKey()

                val nextPage: Int? = key?.nextPage

                // You must explicitly check if the page key is null when
                // appending, since null is only valid for initial load.
                // If you receive null for APPEND, that means you have
                // reached the end of pagination and there are no more
                // items to load.
                if (key?.nextPage == null) {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }

                nextPage
            }
        }

        // Suspending network load via Retrofit. This doesn't need to be
        // wrapped in a withContext(Dispatcher.IO) { ... } block since
        // Retrofit's Coroutine CallAdapter dispatches on a worker
        // thread.
        if (loadKey == null) {
            val configResult = remoteSource.getConfiguration()
            if (configResult.isSuccess()) {
                configResult.getResultOrNull()?.images?.let {
                    localSource.saveConfiguration(it, loadType)
                }
            } else {
                return MediatorResult.Error(Throwable(configResult.getErrorOrNull()))
            }
        }
        val page = loadKey ?: 1
        val result = remoteSource.getMovies(page)
        return if (result.isSuccess()) {
            val data = result.getResultOrNull()
            localSource.saveMovies(
                data?.results ?: emptyList(),
                loadType, page
            )
            val last = (page == data?.totalPages)
            MediatorResult.Success(
                endOfPaginationReached = last
            )
        } else {
            MediatorResult.Error(Throwable(result.getErrorOrNull()))
        }
    }
}
