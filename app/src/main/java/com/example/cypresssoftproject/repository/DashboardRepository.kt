package com.example.cypresssoftproject.repository

import com.example.cypresssoftproject.database.CypressSoftDatabase
import com.example.cypresssoftproject.design.dashboard.interf.DashboardTitleChangeInterf
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.model.merger.DashboardMergerResponse
import com.example.cypresssoftproject.model.singleimages.DashboardSingleImagesResponse
import com.example.cypresssoftproject.service.RetrofitService
import com.example.cypresssoftproject.utils.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class DashboardRepository(
    private val retrofitService: RetrofitService,
    private val db: CypressSoftDatabase,
    private val networkHelper: NetworkHelper,
    private val dashboardTitleChangeInterf: DashboardTitleChangeInterf
) {

    suspend fun getAlbumList(): Response<List<DashboardAlbumResponse>> {

        if (getAlbumFromDatabase().isEmpty()) {
            if (networkHelper.isNetworkConnected()) {
                dashboardTitleChangeInterf.changeTitle(
                    "From API Online",
                    "Below Data is Fetched from API response."
                )
                val responseAlbumListLiveData = retrofitService.getAlbumList()

                val response = responseAlbumListLiveData.body()
                if (responseAlbumListLiveData.isSuccessful && response != null && response.isNotEmpty())
                    storeAlbumToDatabase(response)
            } else {
                dashboardTitleChangeInterf.changeTitle(
                    "No Internet",
                    "No Internet. Please Turn ON your Internet Connectivity for fetch the data."
                )
            }
        } else {
            dashboardTitleChangeInterf.changeTitle(
                "From Room Database Offline",
                "Below Data is Fetched from Room Database. It will fetch all the data and then after progress bar will be disappear."
            )
        }

        return Response.success(getAlbumFromDatabase())

    }

    suspend fun getFullImageList(): Response<List<DashboardImagesResponse>> {

        if (getImageFromDatabase().isEmpty()) {
            if (networkHelper.isNetworkConnected()) {
                val responseImagesList = retrofitService.getFullImageList()

                val response = responseImagesList.body()
                if (responseImagesList.isSuccessful && response != null && response.isNotEmpty())
                    storeImageToDatabase(response)
            }
        }
        return Response.success(getImageFromDatabase())
    }

    suspend fun getMergerOfTwoResponse(): List<DashboardMergerResponse> {
        val albumData = getAlbumFromDatabase()
        val mergerList: ArrayList<DashboardMergerResponse> = arrayListOf()

        for (element in albumData) {
            val imageData = getImageListFromAlbumId(element.id)

            val mergerListData = DashboardMergerResponse(
                element.userId,
                element.id,
                element.title,
                imageData
            )
            mergerList.add(mergerListData)
        }

        return mergerList
    }

    suspend fun getSingleImageList(albumId: String): Response<List<DashboardSingleImagesResponse>> {
        return retrofitService.getSingleImageList(albumId)
    }


    private suspend fun storeAlbumToDatabase(response: List<DashboardAlbumResponse>) =
        withContext(Dispatchers.IO) {
            db.tableAlbumDao().insertAlbum(response)
        }

    private suspend fun storeImageToDatabase(response: List<DashboardImagesResponse>) =
        withContext(Dispatchers.IO) {
            db.tableImageDao().insertImage(response)
        }

    internal suspend fun getPendingImageDownloadList(): List<DashboardImagesResponse> =
        withContext(Dispatchers.IO) { db.tableImageDao().getPendingImageDownloadList() }

    internal suspend fun updateImageLocal(id: String, localUrl: String) =
        withContext(Dispatchers.IO) { db.tableImageDao().updateImageLocal(id, localUrl) }

    private suspend fun getAlbumFromDatabase(): List<DashboardAlbumResponse> =
        withContext(Dispatchers.IO) {
            db.tableAlbumDao().getAlbum()
        }

    private suspend fun getImageFromDatabase(): List<DashboardImagesResponse> =
        withContext(Dispatchers.IO) {
            db.tableImageDao().getImage()
        }

    internal suspend fun getImageListFromAlbumId(albumId: String): List<DashboardImagesResponse> =
        withContext(Dispatchers.IO) {
            db.tableImageDao().getImageListFromAlbumId(albumId)
        }

    internal suspend fun getImageFromImageId(imageId: String): String? =
        withContext(Dispatchers.IO) {
            db.tableImageDao().getImageFromImageId(imageId)
        }
}