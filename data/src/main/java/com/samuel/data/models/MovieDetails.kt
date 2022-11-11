package com.samuel.data.models

data class MovieDetails(
    val adult: Boolean,
    val backdropPath: String? = null,
    val budget: Long,
    val genres: List<String> = arrayListOf(),
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
    val imageConfigs: ImageConfigs,
    val productionCountries: List<String>,
    val revenue: Long,
    val runtime: Int,
    val status: String,
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

    fun getDropUrl(width: String): String {
        return imageConfigs.secureBaseUrl.plus(width).plus(backdropPath)
    }

    fun getMovieGenres(): String {
        val builder = StringBuilder()
        val last = genres.size - 1
        for (i in genres.indices) {
            builder.append(genres[i])
            if (i != 0 && i != last) {
                builder.append(", ")
            }
        }
        return builder.toString()
    }

    fun getMovieCountries(): String {
        val builder = StringBuilder()
        val last = productionCountries.size - 1
        for (i in productionCountries.indices) {
            builder.append(productionCountries[i])
            if (i != last) {
                builder.append(", ")
            }
        }
        return builder.toString()
    }

    fun getMovieLength(): String {
        val hours = runtime / 60
        val minutes = runtime % 60
        return "${hours}h:${String.format("%02d", minutes)}m"
    }
}