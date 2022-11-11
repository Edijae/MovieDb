package com.samuel.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.samuel.data.datasource.local.LocalSource
import com.samuel.data.datasource.local.database.AppDatabase
import com.samuel.data.datasource.local.database.entities.ConfigurationEntity
import com.samuel.data.datasource.remote.RemoteSource
import com.samuel.data.datasource.remote.entites.NetworkMovie
import com.samuel.data.datasource.remote.responses.MoviesResponse
import com.samuel.data.datasource.remote.services.MovieService
import com.samuel.data.models.ImageConfigs
import com.samuel.data.models.MovieDetails
import com.samuel.data.repositoryImpl.MoviesRepositoryImpl
import com.samuel.data.utilities.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class MoviesRepositoryImplTest {

    private lateinit var configEntity: ConfigurationEntity
    private lateinit var config: ImageConfigs
    private lateinit var localSource: LocalSource
    private lateinit var remoteSource: RemoteSource
    private var annotations: AutoCloseable? = null

    @Mock
    private lateinit var movieService: MovieService

    private var mockDb: AppDatabase? = null

    private lateinit var networkMovie: NetworkMovie

    private lateinit var movieDetail: MovieDetails

    private lateinit var response: MoviesResponse

    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        mockDb = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()

        annotations = MockitoAnnotations.openMocks(this)

        response = MoviesResponse(1, emptyList(), 1, 1)
        remoteSource = RemoteSource(movieService)
        localSource = LocalSource(mockDb!!)
        repository = MoviesRepositoryImpl(localSource, remoteSource)
        networkMovie = NetworkMovie(
            false, null, emptyList<Int>(),
            0, emptyList(), 1, "", "", "", "",
            "", "", false, 0.0, 0, 0.0, emptyList(),
            0, 0, ""
        )
        config = ImageConfigs(
            "",
            emptyList(), emptyList(), emptyList(), emptyList(), emptyList()
        )
        configEntity = ConfigurationEntity()
        movieDetail = MovieDetails(
            false, null, 0, emptyList(),
            1, "", "", "", "", "", "",
            false, 0f, 0, 0.0, config,
            emptyList(), 0, 0, ""
        )
    }

    @After
    fun release() {
        annotations?.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieSuccess() {
        return runTest {
            given(movieService.movieDetails(1))
                .willReturn(Response.success(networkMovie))
            mockDb?.configDao()?.insertOrReplace(configEntity)
            val result = repository.getMovieDetails(1).toList()
            println(result)
            assertEquals(Result.Success(movieDetail), result[result.size - 2])
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getMovieFailure() {
        return runTest {
            given(movieService.movieDetails(2))
                .willReturn(Response.error(400, "".toResponseBody(null)))
            val result = repository.getMovieDetails(2).toList()
            println(result)
            assertEquals(
                Result.Failure("An error occurred. Please try again"),
                result[result.size - 2]
            )
        }
    }

    @After
    fun tearDown() {
        mockDb?.clearAllTables()
        mockDb?.close()
    }
}