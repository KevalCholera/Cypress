package com.example.cypresssoftproject.database.dao

import androidx.room.*
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.utils.AlbumTable.ALBUM_TABLE

@Entity(tableName = ALBUM_TABLE)
@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbum(listOfUser: List<DashboardAlbumResponse>)

    @Query("SELECT * FROM $ALBUM_TABLE")
    fun getAlbum(): List<DashboardAlbumResponse>

}