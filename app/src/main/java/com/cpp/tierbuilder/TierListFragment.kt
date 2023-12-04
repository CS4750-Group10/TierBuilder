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
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Lifecycle

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

        // Initialize recycler view with its adapter
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = TierRowAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

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
                        val newTierRow = TierRow("New Title", adapter.itemCount + 1, "Color", listOf())
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

        photoBankFragment.setPhotoDragListener(this)

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

    override fun onPhotoDrag(imageUrl: String) {
    }



}
