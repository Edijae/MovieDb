package com.samuel.data.datasource.remote.entites

import com.google.gson.annotations.SerializedName
import com.samuel.data.datasource.local.database.entities.MovieDetailsEntity
import com.samuel.data.datasource.local.database.entities.MovieEntity
import com.samuel.data.models.ImageConfigs
import com.samuel.data.models.MovieDetails

internal data class NetworkMovie(
    @SerializedName("adult") var adult: Boolean,
    @SerializedName("backdrop_path") var backdropPath: String? = null,
    @SerializedName("genre_ids") var genreIds: List<Int> = arrayListOf(),
    val budget: Long,
    val genres: List<NetworkGenre> = arrayListOf(),
    @SerializedName("id") var id: Int,
    @SerializedName("original_language") var originalLanguage: String,
    @SerializedName("original_title") var originalTitle: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var posterPath: String? = null,
    @SerializedName("release_date") var releaseDate: String,
    @SerializedName("title") var title: String,
    @SerializedName("video") var video: Boolean,
    @SerializedName("vote_average") var voteAverage: Double,
    @SerializedName("vote_count") var voteCount: Int,
    @SerializedName("popularity") var popularity: Double,
    @SerializedName("production_countries")
    val productionCountries: List<NetworkCountry>,
    val revenue: Long,
    val runtime: Int,
    val status: String,
)

internal fun NetworkMovie.asEntity() = MovieEntity(
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
    voteAverage = voteAverage,
    voteCount = voteCount,
)

internal fun NetworkMovie.asExternalModel(config: ImageConfigs) = MovieDetails(
    id = id,
    adult = adult,
    originalLanguage = originalLanguage,
    backdropPath = backdropPath,
    genres = genres.map { it.name },
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage.toFloat(),
    voteCount = voteCount,
    imageConfigs = config,
    budget = budget,
    productionCountries = productionCountries.map { it.name },
    revenue = revenue,
    runtime = runtime,
    status = status
)

internal fun NetworkMovie.asDetailEntity() = MovieDetailsEntity(
    id = id,
    adult = adult,
    originalLanguage = originalLanguage,
    backdropPath = backdropPath,
    genres = genres.map { it.name },
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
    budget = budget,
    productionCountries = productionCountries.map { it.name },
    revenue = revenue,
    runtime = runtime,
    status = status
)