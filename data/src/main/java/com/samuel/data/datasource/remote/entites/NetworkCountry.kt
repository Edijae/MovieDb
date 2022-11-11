package com.samuel.data.datasource.remote.entites

import com.google.gson.annotations.SerializedName

internal data class NetworkCountry(
    @SerializedName("iso_3166_1") var iso: String,
    var name: String
)
