package com.samuel.data.datasource.remote.responses

import NetworkImageConfig
import com.google.gson.annotations.SerializedName

internal data class ConfigurationResponse(
    val images: NetworkImageConfig,
    @SerializedName("change_keys") val changeKeys: List<String>
)