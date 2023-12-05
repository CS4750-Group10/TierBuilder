package com.cpp.tierbuilder

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// PhotoBankFragment.kt
class PhotoBankFragment : Fragment() {

    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter

    interface PhotoDragListener {
        fun onImageSelected(imageUrl: String)
    }

    private var photoDragListener: PhotoDragListener? = null

    fun setPhotoDragListener(listener: PhotoDragListener) {
        photoDragListener = listener
    }

    private fun notifyImageSelected(imageUrl: String) {
        photoDragListener?.onImageSelected(imageUrl)
        Log.d("PhotoBankFragment", "Image selected: $imageUrl")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize RecyclerView and its adapter
        photoRecyclerView = view.findViewById(R.id.photoRecyclerView)

        // Update the instantiation of PhotoAdapter
        photoAdapter = PhotoAdapter(requireContext()) { imageUrl ->
            notifyImageSelected(imageUrl)
        }

        photoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        photoRecyclerView.adapter = photoAdapter

        // Get reference to the 'Upload' button
        val btnUpload: Button = view.findViewById(R.id.upload)

        // Set OnClickListener for the 'Upload' button
        btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        Log.d("PhotoBankFragment", "onViewCreated called")

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_bank, container, false)

        // Initialize RecyclerView and its adapter
        photoRecyclerView = view.findViewById(R.id.photoRecyclerView)

        // Update the instantiation of PhotoAdapter
        photoAdapter = PhotoAdapter(requireContext()) { imageUrl ->
            // Handle click or drag-and-drop of images here
            // You can pass the image URL to the tier list or perform other actions
        }

        photoRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        photoRecyclerView.adapter = photoAdapter

        // Get reference to the 'Upload' button
        val btnUpload: Button = view.findViewById(R.id.upload)

        // Set OnClickListener for the 'Upload' button
        btnUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        Log.d("PhotoBankFragment", "onCreateView called")

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            // Get the selected image URI
            val selectedImageUri: Uri? = data?.data

            // Perform your image upload logic here
            if (selectedImageUri != null) {
                // You can use the selectedImageUri to upload the image to your server or storage
                // For simplicity, add the selected image URL to the adapter
                photoAdapter.addImage(selectedImageUri.toString())
            }
        }
    }


    fun getImageList(): List<String> {
        return photoAdapter.getImageList()
    }

    fun setImageList(imageList: List<String>) {
        photoAdapter.setImages(imageList)
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}
