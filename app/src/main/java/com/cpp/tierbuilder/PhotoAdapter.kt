package com.cpp.tierbuilder

import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide

class PhotoAdapter(
    private val context: Context,
    var onImageClick: ((String) -> Unit)? = null
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val imageUrls: MutableList<String> = mutableListOf()


    // represents the view holder for each item in the RecyclerView
    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView) // Replace with your actual image view ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false) // Replace with your actual layout file
        return PhotoViewHolder(view)
    }

    // Binds the data to the views
    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val imageUrl = imageUrls[position]

        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener { onImageClick?.let { it1 -> it1(imageUrl) } }
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    fun setImages(images: List<String>) {
        imageUrls.clear()
        imageUrls.addAll(images)
        notifyDataSetChanged()
    }

    fun addImage(imageUrl: String) {
        imageUrls.add(imageUrl)
        notifyItemInserted(imageUrls.size - 1)
    }

    fun getImageList(): List<String> {
        return imageUrls.toList()
    }
}
