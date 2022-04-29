package com.example.cypresssoftproject.database.dao

import androidx.room.*
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.utils.AlbumTable.ALBUM_TABLE
import com.example.cypresssoftproject.utils.AlbumTable.ID
import com.example.cypresssoftproject.utils.AlbumTable.IMAGE_LIST

@Entity(tableName = ALBUM_TABLE)
@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //same data replace
    fun insertAlbum(listOfUser: List<DashboardAlbumResponse>)

    @Query("SELECT * FROM $ALBUM_TABLE")
    fun getAlbum(): List<DashboardAlbumResponse>

}