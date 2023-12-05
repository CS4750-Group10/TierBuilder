package com.cpp.tierbuilder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle

class TierListFragment : Fragment(), TierRowEditListener, TierRowAdapter.ImageAdditionListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TierRowAdapter
    private lateinit var fragmentContainer: FrameLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tier_list, container, false)

        // Set up the Toolbar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        // Initialize recycler view with its adapter
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = TierRowAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        fragmentContainer = view.findViewById(R.id.fragmentContainer)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Initialize Options Menu functionality
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tier_list_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_add -> {
                        val newTierRow = TierRow("New Title", adapter.itemCount + 1,
                            "Color", mutableListOf())
                        adapter.addRow(newTierRow)
                        true
                    }
                    R.id.menu_delete -> {
                        val lastPosition = adapter.itemCount - 1
                        if (lastPosition >= 0) {
                            adapter.deleteRow(lastPosition)
                        }
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Find the FrameLayout container in the current fragment's view
        val fragmentContainer = view.findViewById<FrameLayout>(R.id.fragmentContainer)

        // Create an instance of PhotoBankFragment
        val photoBankFragment = PhotoBankFragment()

        // Replace any existing fragments in the container with the PhotoBankFragment
        childFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, photoBankFragment)
            .commit()
    }

    override fun onEditTitleClicked(position: Int) {
        // Implement the edit title logic here
        val tierRow = adapter.getTierRow(position)
        tierRow.isEditing = true
        adapter.notifyItemChanged(position)
    }

    override fun onAddImageClicked(position: Int) {
        Log.d("TierListFragment", "onAddImageClicked called for position $position")
        // Open PhotoBankFragment to allow image selection for the specific tier row
        openPhotoBankFragment(position)
    }

    private fun openPhotoBankFragment(position: Int) {
        // Assuming you have a reference to the existing PhotoBankFragment
        val photoBankFragment = childFragmentManager.findFragmentById(R.id.fragmentContainer) as? PhotoBankFragment

        // Set a callback listener for image selection
        photoBankFragment?.setPhotoDragListener(object : PhotoBankFragment.PhotoDragListener {
            override fun onImageSelected(imageUrl: String) {
                // Handle the selected image URL and add it to the tier row
                handleImageSelection(position, imageUrl)
            }
        })
    }

    private fun handleImageSelection(position: Int, imageUrl: String) {
        val tierRow = adapter.getTierRow(position)
        tierRow.images.add(imageUrl)
        adapter.notifyItemChanged(position)
    }
}
