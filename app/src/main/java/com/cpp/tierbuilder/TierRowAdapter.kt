package com.cpp.tierbuilder


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class TierRowAdapter(
    private val tierRows: List<TierRow>,
    private val onRowClickListener: (Int) -> Unit
) : RecyclerView.Adapter<TierRowAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleContainer: LinearLayout = itemView.findViewById(R.id.titleContainer)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val imagesContainer: LinearLayout = itemView.findViewById(R.id.imagesContainer)
        // Add references to other views as needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tier_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tierRow = tierRows[position]

        // Bind data to the ViewHolder components
        holder.titleTextView.text = tierRow.title
        holder.titleContainer.setBackgroundColor(tierRow.color) // Set the background color
        // Set other views as needed

        // Clear existing ImageViews
        holder.imagesContainer.removeAllViews()

        // For images, you can use a loop to dynamically add ImageViews based on the images list

        for (imageUri in tierRow.imageUris) {
            val imageView = ImageView(holder.imagesContainer.context)
            val params = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1f
            )
            imageView.layoutParams = params
            // Use your preferred image loading library to load images from URI
            // For simplicity, let's assume you have a function loadImageFromUri
            loadImageFromUri(imageView, imageUri)
            holder.imagesContainer.addView(imageView)
        }

        holder.itemView.setOnClickListener {
            onRowClickListener.invoke(position)
        }
    }

    private fun loadImageFromUri(imageView: ImageView, imageUri: String) {
        Glide.with(imageView.context)
            .load(imageUri)
            .centerCrop()
            //.placeholder(R.drawable.smoke_foreground) // Placeholder image while loading
            .into(imageView)
    }

    override fun getItemCount(): Int {
        return tierRows.size
    }
}