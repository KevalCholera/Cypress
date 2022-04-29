package com.example.cypresssoftproject.database.dao

import androidx.room.*
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.utils.SingleImageTable.SINGLE_IMAGE_TABLE

@Entity(tableName = SINGLE_IMAGE_TABLE)
@Dao
interface SingleImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingleImage(listOfUser: List<DashboardAlbumResponse>)

    @Query("SELECT * FROM $SINGLE_IMAGE_TABLE")
    fun getSingleImage(): List<DashboardAlbumResponse>
}