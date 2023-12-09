package com.cpp.tierbuilder

import androidx.fragment.app.Fragment

class SavedListsFragment:Fragment() {

}
//import android.os.Bundle
//import android.util.Log
//import android.view.ActionMode
//import android.view.LayoutInflater
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.lifecycleScope
//import androidx.lifecycle.repeatOnLifecycle
//import androidx.navigation.fragment.findNavController
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.cpp.tierbuilder.databinding.FragmentSavedListsBinding
//import kotlinx.coroutines.launch
//import java.util.UUID
//
//class SavedListsFragment : Fragment() {
//
//    private var _binding: FragmentSavedListsBinding? = null
//    private val binding
//        get() = checkNotNull(_binding) {
//            "Cannot access binding because it is not null. Is the view visible?"
//        }
//
//    private val savedListsViewModel: SavedListsViewModel by viewModels()
//
//    // Create an action mode menu bar
//    private var actionMode: ActionMode? = null
//    private val actionModeCallback = object : ActionMode.Callback {
//        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
//            val inflater: MenuInflater = mode.menuInflater
//            inflater.inflate(R.menu.saved_lists_menu, menu)
//            return true
//        }
//
//        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
//            return false
//        }
//
//        override fun onActionItemClicked(mode: ActionMode, menuItem: MenuItem): Boolean {
//            when (menuItem.itemId) {
//                R.id.copy_tierlist -> {
//                    viewLifecycleOwner.lifecycleScope.launch {
//                        for (listId in savedListsViewModel.selectedLists) {
//                            savedListsViewModel.copyTierList(listId)
//                        }
//                    }
//                    mode.finish()
//                    Log.d("SavedListsFragment", "copy")
//                    return true
//                }
//                R.id.delete_tierlist -> {
//                    viewLifecycleOwner.lifecycleScope.launch {
//                        for (listId in savedListsViewModel.selectedLists) {
//                            savedListsViewModel.deleteTierList(listId)
//                        }
//                    }
//                    mode.finish()
//                    Log.d("SavedListsFragment", "delete")
//                    return true
//                }
//                else -> return false
//            }
//        }
//
//        override fun onDestroyActionMode(mode: ActionMode) {
//            mode.finish()
//            actionMode = null
//            savedListsViewModel.clearSelections()
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = FragmentSavedListsBinding.inflate(inflater, container, false)
//        binding.savedTierlistRecyclerView.layoutManager = LinearLayoutManager(context)
//        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.savedListsToolbar)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                savedListsViewModel.tierlists.collect { tierlists ->
//                    // Initalize RecyclerView's adapter with short and long click functions
//                    binding.savedTierlistRecyclerView.adapter =
//                        SavedListsAdapter(tierlists, savedListsViewModel,
//                            onListClicked = { tierListId ->
//                            findNavController().navigate(
//                                SavedListsFragmentDirections.loadTierList(tierListId)
//                            )
//                        }, onListLongClicked = { tierlistId ->
//                            if (actionMode == null) {
//                                actionMode = requireActivity().startActionMode(actionModeCallback)
//                            }
//                            savedListsViewModel.toggleSelection(tierlistId)
//                            true
//                        })
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}