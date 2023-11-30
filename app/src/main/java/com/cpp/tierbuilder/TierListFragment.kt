package com.cpp.tierbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentContainerView

class TierListFragment : Fragment(), TierRowEditListener,PhotoBankFragment.PhotoDragListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TierRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tier_list, container, false)

        // Set up the Toolbar
        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)

        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = TierRowAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        setHasOptionsMenu(true) // This line is important to indicate that the fragment has its own options menu

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the FrameLayout container in the current fragment's view
        val fragmentContainer = view.findViewById<FrameLayout>(R.id.fragmentContainer)

        // Create an instance of PhotoBankFragment
        val photoBankFragment = PhotoBankFragment()

        photoBankFragment.setPhotoDragListener(this)

        // Replace any existing fragments in the container with the PhotoBankFragment
        childFragmentManager.beginTransaction()
            .replace(fragmentContainer.id, photoBankFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.tier_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> {
                val newTierRow = TierRow("New Title", adapter.itemCount + 1, "Color", listOf())
                adapter.addRow(newTierRow)
                return true
            }
            R.id.menu_delete -> {
                val lastPosition = adapter.itemCount - 1
                if (lastPosition >= 0) {
                    adapter.deleteRow(lastPosition)
                }
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onEditTitleClicked(position: Int) {
        // Implement the edit title logic here
        val tierRow = adapter.getTierRow(position)
        tierRow.isEditing = true
        adapter.notifyItemChanged(position)
    }

    override fun onPhotoDrag(imageUrl: String) {
        // Example: Display a toast message with the dragged photo's URL
        Toast.makeText(requireContext(), "Dragged Photo URL: $imageUrl", Toast.LENGTH_SHORT).show()

        // Example: Add the dragged photo to your tier list
        val newItem = Item(name = "New Item", imageFileName = imageUrl)
        val tierRow = TierRow("New Title", adapter.itemCount + 1, "Color", listOf(newItem))
        adapter.addRow(tierRow)
    }



}
