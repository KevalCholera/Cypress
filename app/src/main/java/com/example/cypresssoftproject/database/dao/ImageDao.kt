package com.example.cypresssoftproject.database.dao

import androidx.room.*
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.utils.ImageTable.ALBUM_ID
import com.example.cypresssoftproject.utils.ImageTable.ID
import com.example.cypresssoftproject.utils.ImageTable.IMAGE_TABLE
import com.example.cypresssoftproject.utils.ImageTable.LOCAL_URL

@Entity(tableName = IMAGE_TABLE)
@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImage(listOfUser: List<DashboardImagesResponse>)

    @Query("SELECT * FROM $IMAGE_TABLE")
    fun getImage(): List<DashboardImagesResponse>

    @Query("SELECT * FROM $IMAGE_TABLE WHERE $ALBUM_ID == :albumId")
    fun getImageListFromAlbumId(albumId: String): List<DashboardImagesResponse>

    @Query("SELECT $LOCAL_URL FROM $IMAGE_TABLE WHERE $ID == :imageId")
    fun getImageFromImageId(imageId: String): String?

    @Query("SELECT * FROM $IMAGE_TABLE WHERE NULLIF($LOCAL_URL, '') IS NULL")
    fun getPendingImageDownloadList(): List<DashboardImagesResponse>

    @Query("UPDATE $IMAGE_TABLE SET ${LOCAL_URL}=:url WHERE ${ID}=:id")
    fun updateImageLocal(id: String, url: String)
}