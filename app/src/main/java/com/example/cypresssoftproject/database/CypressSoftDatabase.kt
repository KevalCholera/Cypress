package com.example.cypresssoftproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cypresssoftproject.database.dao.AlbumDao
import com.example.cypresssoftproject.database.dao.ImageDao
import com.example.cypresssoftproject.database.dao.SingleImageDao
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.model.singleimages.DashboardSingleImagesResponse
import com.example.cypresssoftproject.utils.PrefUtilsConstants.DATABASE_NAME

@Database(
    entities = [DashboardAlbumResponse::class, DashboardImagesResponse::class, DashboardSingleImagesResponse::class],
    version = 1,
    exportSchema = true
)
abstract class CypressSoftDatabase : RoomDatabase() {

    abstract fun tableAlbumDao(): AlbumDao
    abstract fun tableImageDao(): ImageDao
    abstract fun tableSingleImageDao(): SingleImageDao

    companion object {

        @Volatile
        private var INSTANCE: CypressSoftDatabase? = null

        fun getDatabaseData(context: Context): CypressSoftDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CypressSoftDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
