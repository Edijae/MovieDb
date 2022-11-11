package com.samuel.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuel.data.models.ImageConfigs
import com.samuel.data.models.MovieDetails

@Entity(tableName = "movie_details")
internal data class MovieDetailsEntity(
    @PrimaryKey
    var id: Int,
    var adult: Boolean,
    var backdropPath: String? = null,
    var genres: List<String> = arrayListOf(),
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var posterPath: String? = null,
    var releaseDate: String,
    var title: String,
    var video: Boolean,
    var voteAverage: Double,
    var voteCount: Int,
    var popularity: Double,
    var productionCountries: List<String> = arrayListOf(),
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val budget: Long,
)

internal fun MovieDetailsEntity.asExternalModel(config: ImageConfigs) = MovieDetails(
    id = id, adult = adult, originalLanguage = originalLanguage, backdropPath = backdropPath,
    genres = genres, originalTitle = originalTitle, overview = overview, popularity = popularity,
    posterPath = posterPath, releaseDate = releaseDate, title = title, video = video,
    voteAverage = voteAverage.toFloat(), voteCount = voteCount, imageConfigs = config,
    productionCountries = productionCountries, revenue = revenue, budget = budget,
    runtime = runtime, status = status
)
