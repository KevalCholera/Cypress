package com.example.cypresssoftproject.design.dashboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.example.cypresssoftproject.R
import com.example.cypresssoftproject.databinding.ItemImagesBinding
import com.example.cypresssoftproject.model.images.DashboardImagesResponse
import java.util.*

class DashboardImageAdapter(
    private val context: Activity,
    private val imageResponse: ArrayList<DashboardImagesResponse>,
    private val isAllImageDownloaded: Boolean
) : RecyclerView.Adapter<DashboardImageAdapter.ImageDataViewHolder>() {
    private lateinit var binding: ItemImagesBinding

    class ImageDataViewHolder(
        val context: Activity,
        private val binding: ItemImagesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            responsePositionalData: DashboardImagesResponse,
            isAllImageDownloaded: Boolean
        ) {

            val url = responsePositionalData.url
            val localUrl = responsePositionalData.localUrl

            if (!isAllImageDownloaded) {
                val theImage = GlideUrl(
                    url, LazyHeaders.Builder()
                        .addHeader("User-Agent", "5")
                        .build()
                )
                val requestOptions = RequestOptions()
                    .fitCenter()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                setImageToImageView(theImage, requestOptions)
            } else {
                setImageToImageView(localUrl.toString())
            }
        }

        private fun setImageToImageView(liveUrl: GlideUrl, requestOptions: RequestOptions) {

            Glide.with(context)
                .load(liveUrl)
                .error(R.drawable.ic_baseline_electric_scooter_24)
                .thumbnail(
                    Glide.with(binding.ivItemImages.context)
                        .load(R.drawable.gif_skeleton_effect)
                )
                .apply(requestOptions)
                .into(binding.ivItemImages)
        }

        private fun setImageToImageView(localUrl: String) {

            Glide.with(context)
                .load(localUrl)
                .thumbnail(
                    Glide.with(binding.ivItemImages.context)
                        .load(R.drawable.gif_skeleton_effect)
                )
                .into(binding.ivItemImages)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDataViewHolder {
        binding = ItemImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ImageDataViewHolder(context, binding)
    }

    override fun getItemCount(): Int {
        return imageResponse.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ImageDataViewHolder, position: Int) {
        val responsePositionalData = imageResponse[position]

        holder.bind(responsePositionalData, isAllImageDownloaded)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addNewData() {
        imageResponse.addAll(imageResponse)
        notifyDataSetChanged()
    }
}