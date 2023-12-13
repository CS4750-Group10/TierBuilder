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

    interface PhotoSelectListener {
        fun onPhotoSelected(imageUri: Uri)
    }

    private var photoSelectListener: PhotoSelectListener? = null

    fun setPhotoSelectListener(listener: PhotoSelectListener) {
        photoSelectListener = listener
    }

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

        photoAdapter.onImageClick = { imageUrl ->
            photoSelectListener?.onPhotoSelected(Uri.parse(imageUrl))
        }


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

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri: Uri? = data?.data
            if (selectedImageUri != null) {
                photoAdapter.addImage(selectedImageUri.toString())
            } else {
                Log.d("PhotoBankFragment", "the selected image URI is null")
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
        const val TAG = "PhotoBankFragment"
    }
}
