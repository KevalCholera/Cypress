package com.example.cypresssoftproject.design.dashboard.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cypresssoftproject.database.CypressSoftDatabase
import com.example.cypresssoftproject.databinding.ItemAlbumBinding
import com.example.cypresssoftproject.design.dashboard.DashboardActivityViewModel
import com.example.cypresssoftproject.model.album.DashboardAlbumResponse
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import kotlinx.coroutines.runBlocking

class DashboardAlbumAdapter(
    private val context: Activity,
    private val albumResponse: ArrayList<DashboardAlbumResponse>,
    private val db: CypressSoftDatabase,
    private val viewModel: DashboardActivityViewModel
) : RecyclerView.Adapter<DashboardAlbumAdapter.AlbumDataViewHolder>() {
    private lateinit var binding: ItemAlbumBinding

    class AlbumDataViewHolder(
        private val binding: ItemAlbumBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            responsePositionalData: DashboardAlbumResponse,
            db: CypressSoftDatabase, context: Activity, viewModel: DashboardActivityViewModel
        ) {
            binding.rvItemAlbumData.layoutManager =
                LinearLayoutManager(
                    binding.rvItemAlbumData.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.rvItemAlbumData.setHasFixedSize(true)
            binding.tvItemAlbumTitle.text = responsePositionalData.title.toString()
            val albumId = responsePositionalData.id
            getImageListFromAlbumId(albumId, db, context, viewModel)
        }

        private fun getImageListFromAlbumId(
            albumId: String,
            db: CypressSoftDatabase,
            context: Activity,
            viewModel: DashboardActivityViewModel
        ) {

            val filteredImageResponse = runBlocking { viewModel.getImageListFromAlbumId(albumId) }
            Log.i("TAG", "bind: ${filteredImageResponse.size}")
            setDataInAdapter(filteredImageResponse, context)
        }

        private fun setDataInAdapter(
            filteredImageResponse: List<DashboardImagesResponse>,
            context: Activity
        ) {
            val adapter =
                DashboardImageAdapter(
                    context,
                    filteredImageResponse as ArrayList<DashboardImagesResponse>
                )
            binding.rvItemAlbumData.adapter = adapter
            addUnlimitedData(adapter)
        }

        private fun addUnlimitedData(adapter: DashboardImageAdapter) {
            binding.rvItemAlbumData.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager =
                        LinearLayoutManager::class.java.cast(recyclerView.layoutManager)
                    val totalItemCount = layoutManager.itemCount
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
        holder.bind(responsePositionalData, db, context, viewModel)
    }

    fun addNewData() {
        albumResponse.addAll(albumResponse)
        notifyDataSetChanged()
    }
}