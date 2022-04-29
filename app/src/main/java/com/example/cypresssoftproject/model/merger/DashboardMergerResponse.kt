package com.example.cypresssoftproject.model.merger

import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.google.gson.annotations.SerializedName

data class DashboardMergerResponse(
    @SerializedName("userId")
    val userId: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("title")
    val title: String?,
    @SerializedName("imageList")
    val imageList: List<DashboardImagesResponse>?
)