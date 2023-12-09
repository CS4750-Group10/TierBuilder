package com.cpp.tierbuilder

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import java.util.UUID

class HomeScreenFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)
        val newListButton: Button = view.findViewById(R.id.newListButton)
        val loadListButton: Button = view.findViewById(R.id.loadListButton)


        newListButton.setOnClickListener {
            findNavController().navigate(R.id.tierListFragment)
        }

        loadListButton.setOnClickListener {
            findNavController().navigate(R.id.savedListsFragment)
        }

        return view
    }
}