package com.example.cypresssoftproject.model.album

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cypresssoftproject.utils.AlbumTable
import com.example.cypresssoftproject.utils.AlbumTable.ALBUM_TABLE
import com.google.gson.annotations.SerializedName

@Keep
@Entity(tableName = ALBUM_TABLE)
data class DashboardAlbumResponse(
    @ColumnInfo(name = AlbumTable.USER_ID)
    @SerializedName("userId")
    val userId: String?,
    @PrimaryKey
    @ColumnInfo(name = AlbumTable.ID)
    @SerializedName("id")
    val id: String,
    @ColumnInfo(name = AlbumTable.TITLE)
    @SerializedName("title")
    val title: String?
)