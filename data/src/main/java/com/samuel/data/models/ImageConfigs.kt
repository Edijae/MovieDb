package com.samuel.data.models

data class ImageConfigs(
    val secureBaseUrl: String,
    val backdropSizes: List<String>,
    val logoSizes: List<String>,
    val posterSizes: List<String>,
    val profileSizes: List<String>,
    val stillSizes: List<String>
) {

    val imageSizes = posterSizes.dropLast(1).map { it.drop(1).toInt() }
}
