package com.samuel.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuel.data.models.ImageConfigs
import com.samuel.data.models.Movie

@Entity(tableName = "movies")
internal data class MovieEntity(
    @PrimaryKey
    var id: Int,
    var adult: Boolean,
    var backdropPath: String? = null,
    var genreIds: List<Int> = arrayListOf(),
    var originalLanguage: String,
    var originalTitle: String,
    var overview: String,
    var posterPath: String? = null,
    var releaseDate: String,
    var title: String,
    var video: Boolean,
    var voteAverage: Double,
    var voteCount: Int,
    var popularity: Double
)

internal fun MovieEntity.asExternalModel(config: ImageConfigs) = Movie(
    id = id,
    adult = adult,
    originalLanguage = originalLanguage,
    backdropPath = backdropPath,
    genreIds = genreIds,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage.toFloat(),
    voteCount = voteCount,
    imageConfigs = config
)
