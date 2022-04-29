package com.example.cypresssoftproject.design.dashboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cypresssoftproject.databinding.ItemAlbumBinding
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import com.example.cypresssoftproject.model.merger.DashboardMergerResponse

class DashboardAlbumAdapter(
    private val context: Activity,
    private val albumResponse: ArrayList<DashboardMergerResponse>,
    private val isAllImageDownloaded: Boolean
) : RecyclerView.Adapter<DashboardAlbumAdapter.AlbumDataViewHolder>() {
    private lateinit var binding: ItemAlbumBinding

    class AlbumDataViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            responsePositionalData: DashboardMergerResponse,
            context: Activity,
            isAllImageDownloaded: Boolean
        ) {
            binding.rvItemAlbumData.layoutManager =
                LinearLayoutManager(
                    binding.rvItemAlbumData.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.rvItemAlbumData.setHasFixedSize(true)
            binding.tvItemAlbumTitle.text = responsePositionalData.title.toString()
            getImageListFromAlbumId(
                context,
                responsePositionalData.imageList,
                isAllImageDownloaded
            )
        }

        private fun getImageListFromAlbumId(
            context: Activity,
            imageList: List<DashboardImagesResponse>?,
            isAllImageDownloaded: Boolean
        ) {
            if (imageList != null) {
                val adapter =
                    DashboardImageAdapter(
                        context,
                        imageList as ArrayList<DashboardImagesResponse>,
                        isAllImageDownloaded
                    )
                binding.rvItemAlbumData.adapter = adapter
                addUnlimitedData(adapter)
            }
        }

        private fun addUnlimitedData(adapter: DashboardImageAdapter) {
            binding.rvItemAlbumData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager!!.itemCount
                    val lastVisible = layoutManager.findLastVisibleItemPosition()
                    val endHasBeenReached = lastVisible + 5 >= totalItemCount
                    if (totalItemCount > 0 && endHasBeenReached) {
                        adapter.addNewData()
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumDataViewHolder {
        binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return AlbumDataViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return albumResponse.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: AlbumDataViewHolder, position: Int) {
        val responsePositionalData = albumResponse[position]
        holder.bind(responsePositionalData, context, isAllImageDownloaded)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewData() {
        albumResponse.addAll(albumResponse)
        notifyDataSetChanged()
    }
}