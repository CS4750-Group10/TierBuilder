package com.cpp.tierbuilder

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.cpp.tierbuilder.databinding.FragmentTierListBinding
import kotlinx.coroutines.launch

class TierListFragment : Fragment(), TierRowEditListener, TierRowAdapter.ImageAdditionListener {

    private var _binding: FragmentTierListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is not null. Is the view visible?"
        }

    private val args: TierListFragmentArgs by navArgs()

    private val tierListViewModel: TierListViewModel by viewModels() {
        TierListViewModelFactory(args.tierlistId)
    }

    private lateinit var tierRowAdapter: TierRowAdapter
    private val photoBankFragment = PhotoBankFragment()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTierListBinding.inflate(inflater, container, false)

        // Set up the Toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.tierlistToolbar)

        // Initialize recycler view with its adapter
        tierRowAdapter = TierRowAdapter(this, this)
        binding.tierRowRecyclerView.adapter = tierRowAdapter
        binding.tierRowRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
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
                        val newTierRow = TierRow("New Title",tierRowAdapter.itemCount + 1,
                            "Color", mutableListOf())
                        tierRowAdapter.addRow(newTierRow)
                        true
                    }
                    R.id.menu_delete -> {
                        val lastPosition = tierRowAdapter.itemCount - 1
                        if (lastPosition >= 0) {
                           tierRowAdapter.deleteRow(lastPosition)
                        }
                        true
                    }
                    R.id.menu_save ->{
                        onSaveButtonClick()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        // Replace any existing fragments in the container with the PhotoBankFragment
        childFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, photoBankFragment)
            .commit()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                tierListViewModel.tierList.collect { tierList ->
                    tierList?.let {
                        tierRowAdapter.setTierRows(tierList.tierRowList)
                        photoBankFragment.setImageList(tierList.pendingList)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Save tier list to database
    private fun onSaveButtonClick() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newTierList = TierList(
                id = args.tierlistId,
                title = "",
                tierRowList = tierRowAdapter.getTierRows(),
                pendingList = photoBankFragment.getImageList()
            )
            tierListViewModel.addTierList(newTierList)
        }
        //show the keyboard to ask for a List Title
            //we could reuse some of caboosesheps code for editing row titles

        //tierList.title = whateverTheUserTyped

        //lastly, save it to the database
            //requires a working understanding of how databases work.
            //but at this point, tierList will contain everything it needs to be saved for later
        //tierListViewModel.addTierList(tierList)

    }

    override fun onEditTitleClicked(position: Int) {
        // Implement the edit title logic here
        val tierRow = tierRowAdapter.getTierRow(position)
        tierRow.isEditing = true

        // Update the tier row list in the database
        tierListViewModel.updateTierList { oldTierList ->
            // Update tier row with new values and put into the list
            val updatedTierRow = tierRow.copy(
                title = tierRow.title,
                isEditing = true
            )
            val updatedTierRowList = oldTierList.tierRowList.toMutableList()
            updatedTierRowList[position] = updatedTierRow

            // Add tier row to a tier
            oldTierList.copy(tierRowList = updatedTierRowList.toList())
        }
        tierRowAdapter.notifyItemChanged(position)
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
        val tierRow = tierRowAdapter.getTierRow(position)

        // Update tier list in database
        tierListViewModel.updateTierList { oldTierList ->
            // Update tierRow with new image url
            val updatedTierRow = tierRow.copy(images = tierRow.images + imageUrl)
            val updatedTierRowList = oldTierList.tierRowList.toMutableList()
            updatedTierRowList[position] = updatedTierRow

            // Create a new tier list with the updated tier row list
            oldTierList.copy(tierRowList = updatedTierRowList.toList())
        }
        tierRowAdapter.notifyItemChanged(position)
    }
}
