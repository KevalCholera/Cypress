package com.example.cypresssoftproject.design.dashboard

import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cypresssoftproject.base.LiveCoroutinesViewModel
import com.example.cypresssoftproject.base.Status
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.album.DashboardAlbumViewState
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.model.images.DashboardImagesViewState
import com.example.cypresssoftproject.model.singleimages.DashboardSingleImagesViewState
import com.example.cypresssoftproject.repository.DashboardRepository
import com.example.cypresssoftproject.utils.STATUS_CODE_FAILURE
import com.example.cypresssoftproject.utils.STATUS_CODE_INTERNAL_ERROR
import com.example.cypresssoftproject.utils.STATUS_CODE_SUCCESS
import com.example.cypresssoftproject.utils.STATUS_CODE_TOKEN_EXPIRE
import kotlinx.coroutines.launch

class DashboardActivityViewModel(
    val context: DashboardActivity,
    private val dashboardRepository: DashboardRepository
) : LiveCoroutinesViewModel() {

    private var _dashboardAlbumResponse: MutableLiveData<DashboardAlbumViewState> =
        MutableLiveData()
    private var _dashboardImagesResponse: MutableLiveData<DashboardImagesViewState> =
        MutableLiveData()
    private var _dashboardSingleImagesResponse: MutableLiveData<DashboardSingleImagesViewState> =
        MutableLiveData()

    val dashboardAlbumResponse: LiveData<DashboardAlbumViewState>
        get() {
            return _dashboardAlbumResponse
        }

    val dashboardImagesResponse: LiveData<DashboardImagesViewState>
        get() {
            return _dashboardImagesResponse
        }

    val dashboardSingleImagesResponse: LiveData<DashboardSingleImagesViewState>
        get() {
            return _dashboardSingleImagesResponse
        }

    private val propertyChangeRegistry = PropertyChangeRegistry()

    init {
        getAlbumList()
    }

    private fun getAlbumList() {

        _dashboardAlbumResponse.postValue(
            DashboardAlbumViewState(
                Status.LOADING,
                null,
                null
            )
        )
        viewModelScope.launch {
            val response = dashboardRepository.getAlbumList()

            launch {

                when (response.code()) {
                    STATUS_CODE_SUCCESS -> {
                        _dashboardAlbumResponse.postValue(
                            DashboardAlbumViewState(
                                Status.SUCCESS,
                                null,
                                response.body()
                            )
                        )
                        getFullImageList()
                    }
                    STATUS_CODE_FAILURE -> {

                        _dashboardAlbumResponse.postValue(
                            DashboardAlbumViewState(
                                Status.FAIL,
                                "Something went wrong",
                                null
                            )
                        )
                    }
                    STATUS_CODE_INTERNAL_ERROR -> {

                        _dashboardAlbumResponse.postValue(
                            DashboardAlbumViewState(
                                Status.ERROR,
                                "Internal Error",
                                null
                            )
                        )
                    }
                    STATUS_CODE_TOKEN_EXPIRE -> {

                        _dashboardAlbumResponse.postValue(
                            DashboardAlbumViewState(
                                Status.UNAUTHORISED,
                                response.message(),
                                null
                            )
                        )
                    }
                    else -> {

                        _dashboardAlbumResponse.postValue(
                            DashboardAlbumViewState(
                                Status.ERROR,
                                "Something went wrong"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getFullImageList() {

        _dashboardImagesResponse.postValue(
            DashboardImagesViewState(
                Status.LOADING,
                null,
                null
            )
        )
        viewModelScope.launch {
            val response = dashboardRepository.getFullImageList()
            launch {

                when (response.code()) {
                    STATUS_CODE_SUCCESS -> {
                        _dashboardImagesResponse.postValue(
                            DashboardImagesViewState(
                                Status.SUCCESS,
                                null,
                                response.body()
                            )
                        )
                    }
                    STATUS_CODE_FAILURE -> {

                        _dashboardImagesResponse.postValue(
                            DashboardImagesViewState(
                                Status.FAIL,
                                "Something went wrong",
                                null
                            )
                        )
                    }
                    STATUS_CODE_INTERNAL_ERROR -> {

                        _dashboardImagesResponse.postValue(
                            DashboardImagesViewState(
                                Status.ERROR,
                                "Internal Error",
                                null
                            )
                        )
                    }
                    STATUS_CODE_TOKEN_EXPIRE -> {

                        _dashboardImagesResponse.postValue(
                            DashboardImagesViewState(
                                Status.UNAUTHORISED,
                                response.message(),
                                null
                            )
                        )
                    }
                    else -> {

                        _dashboardImagesResponse.postValue(
                            DashboardImagesViewState(
                                Status.ERROR,
                                "Something went wrong"
                            )
                        )
                    }
                }
            }
        }
    }

    private fun getSingleImageList(albumId: String) {

        _dashboardSingleImagesResponse.postValue(
            DashboardSingleImagesViewState(
                Status.LOADING,
                null,
                null
            )
        )
        viewModelScope.launch {
            val response = dashboardRepository.getSingleImageList(albumId)
            launch {

                when (response.code()) {
                    STATUS_CODE_SUCCESS -> {

                        _dashboardSingleImagesResponse.postValue(
                            DashboardSingleImagesViewState(
                                Status.SUCCESS,
                                null,
                                response.body()
                            )
                        )
                    }
                    STATUS_CODE_FAILURE -> {

                        _dashboardSingleImagesResponse.postValue(
                            DashboardSingleImagesViewState(
                                Status.FAIL,
                                "Something went wrong",
                                null
                            )
                        )
                    }
                    STATUS_CODE_INTERNAL_ERROR -> {

                        _dashboardSingleImagesResponse.postValue(
                            DashboardSingleImagesViewState(
                                Status.ERROR,
                                "Internal Error",
                                null
                            )
                        )
                    }
                    STATUS_CODE_TOKEN_EXPIRE -> {

                        _dashboardSingleImagesResponse.postValue(
                            DashboardSingleImagesViewState(
                                Status.UNAUTHORISED,
                                response.message(),
                                null
                            )
                        )
                    }
                    else -> {

                        _dashboardSingleImagesResponse.postValue(
                            DashboardSingleImagesViewState(
                                Status.ERROR,
                                "Something went wrong"
                            )
                        )
                    }
                }
            }
        }
    }

    fun getAlbumListLiveResponse(): List<DashboardAlbumResponse>? {
        return dashboardAlbumResponse.value?.response
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        propertyChangeRegistry.add(callback)
    }

    suspend fun getDownloadList(): List<DashboardImagesResponse> = dashboardRepository.getDownloadList()

    suspend fun updateImageLocal(id: String, localUrl: String) = dashboardRepository.updateImageLocal(id, localUrl)

    suspend fun getImageListFromAlbumId(albumId: String) = dashboardRepository.getImageListFromAlbumId(albumId)

    suspend fun getImageFromImageId(imageId: String) = dashboardRepository.getImageFromImageId(imageId)


}