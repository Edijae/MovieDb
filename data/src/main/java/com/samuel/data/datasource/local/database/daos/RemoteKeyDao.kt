package com.samuel.data.datasource.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuel.data.datasource.local.database.entities.RemoteKeyEntity

@Dao
internal interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun remoteKeyById(id: Int): RemoteKeyEntity?

    @Query("DELETE FROM remote_keys WHERE id = :id")
    suspend fun deleteById(id: Int)
}

