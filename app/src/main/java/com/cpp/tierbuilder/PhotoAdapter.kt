package com.cpp.tierbuilder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PhotoAdapter(
    private val context: Context,
    private val onImageClick: (String) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    public val imageUrls: MutableList<String> = mutableListOf()

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val btnAdd: ImageButton = itemView.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false) // Replace with your actual layout file
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        if (position == imageUrls.size) {
            // This is the last position, show the "Add" button
            holder.imageView.visibility = View.GONE
            holder.btnAdd.visibility = View.VISIBLE
            holder.btnAdd.setOnClickListener {
                // Trigger the image upload process
                // You can open a file picker or camera intent to choose/upload an image
                // For simplicity, I'm assuming you have a method for adding a new image
                onImageClick.invoke("new_image_url_placeholder")
            }
        } else {
            // Show the image
            holder.imageView.visibility = View.VISIBLE
            holder.btnAdd.visibility = View.GONE

            val imageUrl = imageUrls[position]

            Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView)

            holder.itemView.setOnClickListener { onImageClick(imageUrl) }
        }
    }


    override fun getItemCount(): Int {
        return imageUrls.size
    }

    fun setImages(images: List<String>) {
        imageUrls.clear()
        imageUrls.addAll(images)
        notifyDataSetChanged()
    }
}
