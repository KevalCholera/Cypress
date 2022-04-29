package com.example.cypresssoftproject.model.singleimages

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cypresssoftproject.utils.SingleImageTable
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = SingleImageTable.SINGLE_IMAGE_TABLE)
data class DashboardSingleImagesResponse(
    @ColumnInfo(name = SingleImageTable.ALBUM_ID)
    @SerializedName("albumId")
    val albumId: String?,
    @PrimaryKey
    @ColumnInfo(name = SingleImageTable.ID)
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = SingleImageTable.TITLE)
    @SerializedName("title")
    val title: String?,
    @ColumnInfo(name = SingleImageTable.URL)
    @SerializedName("url")
    val url: String?,
    @ColumnInfo(name = SingleImageTable.THUMBNAIL_URL)
    @SerializedName("thumbnailUrl")
    val thumbnailUrl: String?,
    @ColumnInfo(name = SingleImageTable.LOCAL_URL)
    @SerializedName("local_url")
    val localUrl: String?,
    @ColumnInfo(name = SingleImageTable.LOCAL_THUMBNAIL_URL)
    @SerializedName("localThumbnailUrl")
    val localThumbnailUrl: String?
)