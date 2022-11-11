import com.google.gson.annotations.SerializedName
import com.samuel.data.datasource.local.database.entities.ConfigurationEntity

data class NetworkImageConfig(

    @SerializedName("base_url") val baseUrl: String,
    @SerializedName("secure_base_url") val secureBaseUrl: String,
    @SerializedName("backdrop_sizes") val backdropSizes: List<String>,
    @SerializedName("logo_sizes") val logoSizes: List<String>,
    @SerializedName("poster_sizes") val posterSizes: List<String>,
    @SerializedName("profile_sizes") val profileSizes: List<String>,
    @SerializedName("still_sizes") val stillSizes: List<String>
)

internal fun NetworkImageConfig.asEntity(): ConfigurationEntity {
    val config = ConfigurationEntity()
    config.id = 1
    config.baseUrl = baseUrl
    config.secureBaseUrl = secureBaseUrl
    config.backdropSizes = backdropSizes
    config.logoSizes = logoSizes
    config.posterSizes = posterSizes
    config.profileSizes = profileSizes
    config.stillSizes = stillSizes

    return config
}