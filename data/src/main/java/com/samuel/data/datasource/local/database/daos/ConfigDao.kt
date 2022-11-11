package com.samuel.data.datasource.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuel.data.datasource.local.database.entities.ConfigurationEntity

@Dao
internal interface ConfigDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(configurationEntity: ConfigurationEntity)

    @Query("SELECT * FROM configurations WHERE id = :id")
    suspend fun getConfigById(id: Int): ConfigurationEntity

    @Query("DELETE FROM configurations WHERE id = :id")
    suspend fun deleteById(id: Int)
}

