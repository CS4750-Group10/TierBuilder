package com.cpp.tierbuilder

import android.graphics.Color
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.cpp.tierbuilder.databinding.FragmentHomeScreenBinding
import kotlinx.coroutines.launch
import java.util.UUID

class HomeScreenFragment : Fragment(){
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

        // Create a new Tier list and go to tier list builder
        binding.newListButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                val newTierList = TierList(
                    id = UUID.randomUUID(),
                    title = "New Tier List",
                    tierRowList = generateDefaultRows()
                )
                homeScreenViewModel.addTierList(newTierList)

                findNavController().navigate(
                    HomeScreenFragmentDirections.createTierList(newTierList.id)
                )
            }
        }

        // Go to Saved Tier Lists
        binding.loadListButton.setOnClickListener {
            findNavController().navigate(
                HomeScreenFragmentDirections.showAllLists()
            )
        }

        return binding.root
    }

    private fun generateDefaultRows(): List<TierRow> {
        // Default TierRow list
        val tierRows = mutableListOf<TierRow>()
        // Set default values
        val defaultLabels = listOf("S", "A", "B", "C", "D", "E", "F")
        val defaultColors = listOf(
            "#FF0000", // Red
            "#FFA500", // Orange
            "#FFFF00", // Yellow
            "#00FF00", // Green
            "#0DD8E6", // Turquoise
            "#9457EB", // Purple
            "#EE82EE", // Violet
        )
        for ((index, label) in defaultLabels.withIndex()) {
            val tierRow = TierRow(label, index + 1, defaultColors[index], mutableListOf())

            tierRows.add(tierRow)
        }

        return tierRows.toList()
    }
}