package com.example.cypresssoftproject.repository

import android.widget.Toast
import com.example.cypresssoftproject.database.CypressSoftDatabase
import com.example.cypresssoftproject.design.dashboard.interf.DashboardTitleChangeInterf
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
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
            dashboardTitleChangeInterf.changeTitle("From API Online")
            if (networkHelper.isNetworkConnected()) {
                val responseAlbumListLiveData = retrofitService.getAlbumList()

                val response = responseAlbumListLiveData.body()
                if (responseAlbumListLiveData.isSuccessful && response != null && response.isNotEmpty())
                    storeAlbumToDatabase(response)
            } else {
                Toast.makeText(networkHelper.getActivity(), "No Internet", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            dashboardTitleChangeInterf.changeTitle("From Room Database Offline")
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
            } else {
                Toast.makeText(networkHelper.getActivity(), "No Internet", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        return Response.success(getImageFromDatabase())
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

    internal suspend fun getDownloadList(): List<DashboardImagesResponse> =
        withContext(Dispatchers.IO) { db.tableImageDao().getDownloadList() }

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