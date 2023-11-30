package com.cpp.tierbuilder

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.util.Collections

class PhotoAdapter(
    private val context: Context,
    private val recyclerView: RecyclerView,
    private val onImageClick: (String) -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private val imageUrls: MutableList<String> = mutableListOf()

    inner class PhotoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView) // Replace with your actual image view ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo, parent, false) // Replace with your actual layout file
        return PhotoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val imageUrl = imageUrls[position]

        Glide.with(context)
            .load(imageUrl)
            .into(holder.imageView)

        holder.itemView.setOnClickListener { onImageClick(imageUrl) }
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

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Notify the adapter of the move
        notifyItemMoved(fromPosition, toPosition)
    }

    // Add this function to swap items in the list
    fun swapItems(fromPosition: Int, toPosition: Int) {
        Collections.swap(imageUrls, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    private val itemTouchHelperCallback = object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            return makeMovementFlags(dragFlags, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            // Handle item move and notify the adapter
            val fromPosition = viewHolder.adapterPosition
            val toPosition = target.adapterPosition
            Collections.swap(imageUrls, fromPosition, toPosition)
            notifyItemMoved(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Not used for drag-and-drop
        }
    }

    init {
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
