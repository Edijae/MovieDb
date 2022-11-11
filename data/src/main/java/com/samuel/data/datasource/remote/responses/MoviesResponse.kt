package com.samuel.data.datasource.remote.responses

import com.google.gson.annotations.SerializedName
import com.samuel.data.datasource.remote.entites.NetworkMovie

internal data class MoviesResponse(
    val page: Int,
    val results: List<NetworkMovie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)