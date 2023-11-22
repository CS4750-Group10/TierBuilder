package com.cpp.tierbuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.cpp.tierbuilder.databinding.FragmentSavedListsBinding
import kotlinx.coroutines.launch

class SavedListsFragment : Fragment() {

    private var _binding: FragmentSavedListsBinding? = null

    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is not null. Is the view visible?"
        }

    private val savedListsViewModel: SavedListsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedListsBinding.inflate(inflater, container, false)
        binding.savedTierlistRecyclerView.layoutManager = LinearLayoutManager(context)
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.savedListsToolbar)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.fragment_saved_lists, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.copy_tierlist -> {
                        true
                    }
                    R.id.delete_tierlist -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                savedListsViewModel.tierlists.collect { tierlists ->
                    binding.savedTierlistRecyclerView.adapter =
                        SavedListsAdapter(tierlists) {tierListId ->
                            findNavController().navigate(
                                SavedListsFragmentDirections.loadTierList(tierListId)
                            )
                        }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}