package com.example.cypresssoftproject.model.images

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cypresssoftproject.utils.ImageTable
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = ImageTable.IMAGE_TABLE)
data class DashboardImagesResponse(
    @ColumnInfo(name = ImageTable.ALBUM_ID)
    @SerializedName("albumId")
    val albumId: String?,
    @PrimaryKey
    @ColumnInfo(name = ImageTable.ID)
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = ImageTable.TITLE)
    @SerializedName("title")
    val title: String?,
    @ColumnInfo(name = ImageTable.URL)
    @SerializedName("url")
    val url: String?,
    @ColumnInfo(name = ImageTable.THUMBNAIL_URL)
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String?,
    @ColumnInfo(name = ImageTable.LOCAL_URL)
    @SerializedName("local_url")
    val localUrl: String?,
    @ColumnInfo(name = ImageTable.LOCAL_THUMBNAIL_URL)
    @SerializedName("localThumbnailUrl")
    val localThumbnailUrl: String?,
    @ColumnInfo(name = ImageTable.SIZE)
    @SerializedName("size")
    val size: String?
)