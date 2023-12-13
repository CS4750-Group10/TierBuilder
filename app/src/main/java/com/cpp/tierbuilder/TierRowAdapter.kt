package com.cpp.tierbuilder

import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexboxLayout
import android.content.Context;
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL


// Add a callback interface
interface TierRowEditListener {
    fun onEditTitleClicked(position: Int)
}

class TierRowAdapter(
    private val editListener: TierRowEditListener,
    private val imageAdditionListener: ImageAdditionListener? = null
) : RecyclerView.Adapter<TierRowAdapter.TierRowViewHolder>() {

    private val tierRows = mutableListOf<TierRow>()

    interface ImageAdditionListener {
        fun onAddImageClicked(position: Int)
    }

    init {
//        val defaultLabels = listOf("S", "A", "B", "C", "D", "E", "F")
//        for ((index, label) in defaultLabels.withIndex()) {
//            val sampleImages = mutableListOf(
//                "https://kitchenatics.com/wp-content/uploads/2020/09/Cheese-pizza-1.jpg", // Replace with actual image URLs
//                "https://www.iheartnaptime.net/wp-content/uploads/2023/04/Pepperoni-Pizza-Recipe-I-Heart-Naptime.jpg"
//            )
//            val tierRow = TierRow(label, index + 1, "Color", sampleImages)
//
//            tierRows.add(tierRow)
//        }
        generateDefaultRows()
    }

    private fun generateDefaultRows() {
        val defaultLabels = listOf("S", "A", "B", "C", "D", "E", "F")
        for ((index, label) in defaultLabels.withIndex()) {
            val tierRow = TierRow(label, index + 1, "Color", mutableListOf())

            tierRows.add(tierRow)
        }
    }

    inner class TierRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // You can access views within your TierRowFragment layout here
        val labelTextView: TextView = itemView.findViewById(R.id.tierTextView)
        val editTitleEditText: EditText = itemView.findViewById(R.id.editTitleEditText)

        val btnAddImage: ImageView = itemView.findViewById(R.id.btnAddImage)

        val imageContainer: FlexboxLayout = itemView.findViewById(R.id.imageContainer)

        fun addImageToFlexbox(imageUrl: String) {
            val imageView = ImageView(itemView.context).apply {
                layoutParams = FlexboxLayout.LayoutParams(
                    200,
                    200
                ).also {
                    // Set a margin if necessary
                    val margin = itemView.resources.getDimensionPixelSize(R.dimen.image_margin)
                    it.setMargins(margin, margin, margin, margin)
                }
                // You may want to adjust the image size here if necessary
            }

            // Load the image using Glide with error handling and logging
            Glide.with(itemView.context)
                .load(imageUrl)
                .error(R.drawable.error_placeholder)  // Use your error placeholder
                .into(imageView)

            // Add the ImageView to the FlexboxLayout
            imageContainer.addView(imageView)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TierRowViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_tier_row, parent, false)
        return TierRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: TierRowViewHolder, position: Int) {
        val tierRow = tierRows[position]
        holder.imageContainer.removeAllViews() // Clear existing images

        tierRow.images.forEach { imageUrl ->
            holder.addImageToFlexbox(imageUrl)
        }

        holder.labelTextView.text = tierRow.title
        holder.labelTextView.setOnClickListener {
            editListener.onEditTitleClicked(position)
        }

        holder.editTitleEditText.inputType =
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

//        for (imageUrl in tierRow.images) {
//            holder.addImageToFlexbox(imageUrl)
//        }

//        // Add ImageViews dynamically to the FlexboxLayout based on the images associated with the tier row
//        tierRow.images.forEach { imageUrl ->
//            holder.addImageToFlexbox(imageUrl)
//        }

        // Update the visibility of views based on the edit mode
        if (tierRow.isEditing) {
            holder.labelTextView.visibility = View.GONE
            holder.editTitleEditText.visibility = View.VISIBLE
            holder.editTitleEditText.setText(tierRow.title)

            // Set a listener to update the title when the user finishes editing
            holder.editTitleEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    tierRow.title = holder.editTitleEditText.text.toString()
                    tierRow.isEditing = false
                    notifyItemChanged(position)
                    // close the keyboard after clicking "Done"
                    val inputMethodManager =
                        holder.editTitleEditText.context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as android.view.inputmethod.InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(holder.editTitleEditText.windowToken, 0)
                    true
                } else {
                    false
                }
            }
        } else {
            holder.labelTextView.visibility = View.VISIBLE
            holder.editTitleEditText.visibility = View.GONE
        }

        when (position) {
            0 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FF0000")) // Red
            1 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FFA500")) // Orange
            2 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#FFFF00")) // Yellow
            3 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#00FF00")) // Green
            4 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#0DD8E6")) // Turquoise
            5 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#9457EB")) // Purple
            6 -> holder.labelTextView.setBackgroundColor(Color.parseColor("#EE82EE")) // Violet
            else -> holder.labelTextView.setBackgroundColor(Color.BLACK) // Default color
        } // You can set the color as per your requirement

        holder.btnAddImage.setOnClickListener {
            // Notify the listener that the add image icon is clicked
            imageAdditionListener?.onAddImageClicked(holder.adapterPosition)
        }

    }

    override fun getItemCount(): Int {
        return tierRows.size
    }

    fun addRow(tierRow: TierRow) {
        tierRows.add(tierRow)
        notifyItemInserted(tierRows.size - 1)
    }

    fun deleteRow(position: Int) {
        if (position in 0 until tierRows.size) {
            tierRows.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getTierRow(position: Int): TierRow {
        return tierRows[position]
    }

    fun getTierRows(): List<TierRow> {  //call this function when user clicks
        return tierRows.toList()
    }

    // Set tier row list
    fun setTierRows(newTierRows: List<TierRow>) {
        tierRows.clear()
        tierRows.addAll(newTierRows)
        notifyDataSetChanged()
    }
}
