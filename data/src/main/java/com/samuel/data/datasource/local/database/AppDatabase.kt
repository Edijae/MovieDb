package com.samuel.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.samuel.data.datasource.local.database.daos.ConfigDao
import com.samuel.data.datasource.local.database.daos.MovieDao
import com.samuel.data.datasource.local.database.daos.MovieDetailsDao
import com.samuel.data.datasource.local.database.daos.RemoteKeyDao
import com.samuel.data.datasource.local.database.entities.ConfigurationEntity
import com.samuel.data.datasource.local.database.entities.MovieDetailsEntity
import com.samuel.data.datasource.local.database.entities.MovieEntity
import com.samuel.data.datasource.local.database.entities.RemoteKeyEntity
import com.samuel.data.utilities.Converters

@TypeConverters(Converters::class)
@Database(
    entities = [MovieEntity::class, RemoteKeyEntity::class,
        ConfigurationEntity::class, MovieDetailsEntity::class], version = 1
)
internal abstract class AppDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, useInMemory: Boolean): AppDatabase {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            } else {
                Room.databaseBuilder(context, AppDatabase::class.java, "movies.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun movieDao(): MovieDao
    abstract fun keyDao(): RemoteKeyDao
    abstract fun configDao(): ConfigDao
    abstract fun movieDetailsDao(): MovieDetailsDao
}