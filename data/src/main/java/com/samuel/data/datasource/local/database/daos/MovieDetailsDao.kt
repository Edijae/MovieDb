package com.samuel.data.datasource.local.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.samuel.data.datasource.local.database.entities.MovieDetailsEntity

@Dao
internal interface MovieDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(detailsEntity: MovieDetailsEntity)

    @Query("SELECT * FROM movie_details WHERE id = :id")
    suspend fun getById(id: Int): MovieDetailsEntity?

    @Query("DELETE FROM movie_details WHERE id = :id")
    suspend fun deleteById(id: Int)
}

