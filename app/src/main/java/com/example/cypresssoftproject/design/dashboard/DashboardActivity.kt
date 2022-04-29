package com.example.cypresssoftproject.design.dashboard

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.format.Formatter.formatShortFileSize
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.cypresssoftproject.R
import com.example.cypresssoftproject.base.BaseActivity
import com.example.cypresssoftproject.base.Status
import com.example.cypresssoftproject.databinding.ActivityDashboardBinding
import com.example.cypresssoftproject.design.dashboard.adapter.DashboardAlbumAdapter
import com.example.cypresssoftproject.design.dashboard.interf.DashboardTitleChangeInterf
import com.example.cypresssoftproject.model.merger.DashboardMergerResponse
import com.example.cypresssoftproject.repository.DashboardRepository
import java.io.File

class DashboardActivity : BaseActivity(), DashboardTitleChangeInterf {
    private val binding: ActivityDashboardBinding by binding(R.layout.activity_dashboard)

    private lateinit var viewModel: DashboardActivityViewModel
    lateinit var albumAdapter: DashboardAlbumAdapter
    private var alreadyStartedDownload = false
    private var isAllImageDownloaded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        responseObservors()
        addUnlimitedData()
    }

    private fun initialize() {
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(
            this,
            DashboardModelFactory(
                DashboardRepository(
                    retrofitService,
                    db,
                    networkHelper,
                    this
                )
            )
        )[DashboardActivityViewModel::class.java]
    }

    private fun responseObservors() {

        viewModel.dashboardAlbumResponse.observe(
            this,
            {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressCircular.visibility = View.GONE

                    }
                    Status.ERROR -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                    Status.LOADING -> {
                        binding.progressCircular.visibility = View.VISIBLE
                    }
                    Status.FAIL -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                    else -> {
                        binding.progressCircular.visibility = View.GONE
                    }
                }
            }
        )
        viewModel.dashboardImagesResponse.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                    val albumResponse = viewModel.getAlbumListLiveResponse()
                    if (albumResponse != null) {
                        setAlbumAdapter(viewModel.getMergerOfTwoResponse())
                    }
                }
                Status.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.FAIL -> {
                    binding.progressCircular.visibility = View.GONE
                }
                else -> {
                    binding.progressCircular.visibility = View.GONE
                }
            }
        })

        viewModel.dashboardSingleImagesResponse.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressCircular.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressCircular.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }
                Status.FAIL -> {
                    binding.progressCircular.visibility = View.GONE
                }
                else -> {
                    binding.progressCircular.visibility = View.GONE
                }
            }
        })
    }

    private fun addUnlimitedData() {
        binding.rvDashboard.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                val totalItemCount = layoutManager!!.itemCount
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                val endHasBeenReached = lastVisible + 5 >= totalItemCount
                if (totalItemCount > 0 && endHasBeenReached) {
                    albumAdapter.addNewData()
                }
            }
        })
    }

    private fun setAlbumAdapter(
        albumResponse: List<DashboardMergerResponse>
    ) {
        isAllImageDownloaded = viewModel.getPendingImageDownloadList().isEmpty()

        albumAdapter =
            DashboardAlbumAdapter(
                this,
                albumResponse as ArrayList<DashboardMergerResponse>, isAllImageDownloaded
            )
        binding.rvDashboard.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvDashboard.setHasFixedSize(true)
        binding.rvDashboard.adapter = albumAdapter
        downloadImages()
    }

    private fun downloadImages() {
        alreadyStartedDownload = true
        val imageResponse = viewModel.getPendingImageDownloadList()

        if (imageResponse.isNotEmpty()) {
            for (i in imageResponse.indices) {

                if (!networkHelper.isNetworkConnected()) {
                    alreadyStartedDownload = false
                    break
                }

                val dataImageResponse = imageResponse[i]

                val theImage = GlideUrl(
                    dataImageResponse.url, LazyHeaders.Builder()
                        .addHeader("User-Agent", "5")
                        .build()
                )

                Glide.with(this@DashboardActivity)
                    .asBitmap()
                    .load(theImage)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            val bitmapToUri =
                                getImageUri(getResizedBitmap(resource, 100)).toString()
                            val fileOriginal = File(bitmapToUri)

                            val fileSizeOriginal =
                                formatShortFileSize(
                                    this@DashboardActivity,
                                    fileOriginal.length()
                                )

                            viewModel.updateImageLocal(
                                dataImageResponse.id,
                                bitmapToUri,
                                fileSizeOriginal
                            )
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {

                        }
                    })
            }
        }
    }

    override fun changeTitle(titleName: String, toastMsg: String) {
//        title = titleName
        binding.tvDashboardMessage.text = toastMsg
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        if (viewModel.getMergerOfTwoResponse().isEmpty()) {
            viewModel.getAlbumList()
        } else {
            if (isConnected && !alreadyStartedDownload)
                downloadImages()
            else
                alreadyStartedDownload = false
        }
    }
}