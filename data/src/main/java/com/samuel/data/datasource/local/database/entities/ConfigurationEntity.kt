package com.samuel.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.samuel.data.models.ImageConfigs

@Entity(tableName = "configurations")
internal class ConfigurationEntity {
    @PrimaryKey
    var id: Int = 1
    var baseUrl: String = ""
    var secureBaseUrl: String = ""
    var backdropSizes: List<String> = emptyList()
    var logoSizes: List<String> = emptyList()
    var posterSizes: List<String> = emptyList()
    var profileSizes: List<String> = emptyList()
    var stillSizes: List<String> = emptyList()

}

internal fun ConfigurationEntity.toExternalModel() = ImageConfigs(
    secureBaseUrl, backdropSizes, logoSizes, posterSizes, profileSizes, stillSizes
)
