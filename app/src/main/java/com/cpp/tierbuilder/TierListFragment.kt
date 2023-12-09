package com.cpp.tierbuilder

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
///
////////////

///
//s
//
class TierListFragment : Fragment() {
    private lateinit var tierListViewModel: TierListViewModel
    private var selectedPosition: Int = -1  // Initialize with an invalid value

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tier_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        tierListViewModel = ViewModelProvider(this).get(TierListViewModel::class.java)

        // Set up the button click listener
        val addButton: Button = view.findViewById(R.id.addImageButton)
        addButton.setOnClickListener {
            // Show a dialog to let the user pick a row
            showRowPickerDialog()
        }

        showAddButton()

        // Observe changes to the tier list data and update the UI
        tierListViewModel.tierList.observe(viewLifecycleOwner) { tierList ->
            val recyclerView: RecyclerView = view.findViewById(R.id.tier_row_recycler_view)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            // Create an instance of TierRowAdapter with the onRowClickListener
            recyclerView.adapter = TierRowAdapter(tierList) {
                // No specific actions needed when a row is clicked
            }
        }


    }

    private fun showRowPickerDialog() {
        val rowTitles = tierListViewModel.tierList.value?.map { it.title }?.toTypedArray() ?: arrayOf()

        val builder = AlertDialog.Builder(requireContext()) //try different import AlertDialog
        builder.setTitle("Select a Row")
        builder.setItems(rowTitles) { _, which ->
            // 'which' is the selected index
            val selectedPosition = which

            // Now, launch the gallery picker
            launchGalleryPicker(selectedPosition)
        }
        builder.show()
    }

    // Function to launch the gallery picker
    private fun launchGalleryPicker(selectedPosition: Int) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, selectedPosition)
    }


    // Handle the result of the gallery picker
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode >= 0 && resultCode == Activity.RESULT_OK && data != null) {
            // Get the selected image URI
            val selectedImageUri: Uri = data.data ?: return

            // Use the requestCode as the selected position
            val selectedPosition = requestCode

            // Assuming you have the selected image URI, add it to the current tier row
            tierListViewModel.addImageToTierRow(selectedPosition, selectedImageUri.toString())
        }
    }

    private fun showAddButton() {
        // Show the add button
        val addButton: Button = requireView().findViewById(R.id.addImageButton)
        addButton.visibility = View.VISIBLE
    }


    companion object {
        private const val PICK_IMAGE_REQUEST_CODE = 123
    }
}























//Im keeping these commented out but we should try to put them back in

        //Initialize Options Menu functionality
//        val menuHost: MenuHost = requireActivity()
//        menuHost.addMenuProvider(object : MenuProvider {
//            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
//                menuInflater.inflate(R.menu.tier_list_menu, menu)
//            }

//            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
//                return when (menuItem.itemId) {
//                    R.id.menu_add -> {
//                        val newTierRow = TierRow("New Title",tierRowAdapter.itemCount + 1,
//                            "Color", mutableListOf())
//                        tierRowAdapter.addRow(newTierRow)
//                        true
//                    }
//                    R.id.menu_delete -> {
//                        val lastPosition = tierRowAdapter.itemCount - 1
//                        if (lastPosition >= 0) {
//                           tierRowAdapter.deleteRow(lastPosition)
//                        }
//                        true
//                    }
//                    R.id.menu_save ->{
//                        onSaveButtonClick()
//                        true
//                    }
//                    else -> false
//                }
//            }


//    override fun onEditTitleClicked(position: Int) {
//        // Implement the edit title logic here
//        val tierRow = tierRowAdapter.getTierRow(position)
//        tierRow.isEditing = true
//
//        // Update the tier row list in the database
//        tierListViewModel.updateTierList { oldTierList ->
//            // Update tier row with new values and put into the list
//            val updatedTierRow = tierRow.copy(
//                title = tierRow.title,
//                isEditing = true
//            )
//            val updatedTierRowList = oldTierList.tierRowList.toMutableList()
//            updatedTierRowList[position] = updatedTierRow
//
//            // Add tier row to a tier
//            oldTierList.copy(tierRowList = updatedTierRowList.toList())
//        }
//        tierRowAdapter.notifyItemChanged(position)
//    }

