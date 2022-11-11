/*
package com.samuel.data.datasource.remote.entites

import com.google.gson.annotations.SerializedName
import com.samuel.data.datasource.local.database.entities.MovieEntity

internal data class NetworkMovieDetails(
    val adult : Boolean,
    @SerializedName("backdrop_path") val backdropPath : String?= null,
    val budget:Int,
    val genres : ArrayList<NetworkGenre> = arrayListOf(),
    val id : Int,
    @SerializedName("original_language") val originalLanguage : String,
    @SerializedName("original_title") val originalTitle : String,
    val overview : String,
    @SerializedName("poster_path") val posterPath : String?= null,
    @SerializedName("release_date") val releaseDate : String,
    @SerializedName("title") val title : String,
    @SerializedName("video") val video : Boolean,
    @SerializedName("vote_average") val voteAverage : Double,
    @SerializedName("vote_count") val voteCount : Int,
    val popularity: Double,
    @SerializedName("production_countries")
    val productionCountries : List<NetworkCountry>,
    val revenue : Int,
    val runtime:Int,
    val status : String,
)*/
