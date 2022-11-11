package com.samuel.data.datasource.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
internal data class RemoteKeyEntity(
    @PrimaryKey
    val id: Int = 1,
    val currentPage: Int?,
    val nextPage: Int?
)
