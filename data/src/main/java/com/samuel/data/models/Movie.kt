package com.samuel.data.models

data class Movie(
    val adult: Boolean,
    val backdropPath: String? = null,
    val genreIds: List<Int> = arrayListOf(),
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val posterPath: String? = null,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Float,
    val voteCount: Int,
    val popularity: Double,
    val imageConfigs: ImageConfigs
) {
    fun getImageSize(width: Int): String {
        var approx = ""
        val last = imageConfigs.imageSizes.size - 1
        for (i in imageConfigs.imageSizes.indices) {
            val size = imageConfigs.imageSizes[i]
            if (size > width) {
                approx = if (i == 0) {
                    "w$size"
                } else {
                    "w${imageConfigs.imageSizes[i - 1]}"
                }
                break
            }
            if (i == last && approx.isEmpty()) {
                approx = imageConfigs.posterSizes.last()
            }
        }
        return approx
    }

    fun getImageUrl(size: String): String {
        return imageConfigs.secureBaseUrl.plus(size).plus(posterPath)
    }
}