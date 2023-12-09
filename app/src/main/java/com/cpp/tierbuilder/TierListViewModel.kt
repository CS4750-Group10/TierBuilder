package com.cpp.tierbuilder

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TierListViewModel : ViewModel() {
    private val _tierList = MutableLiveData<List<TierRow>>()
    val tierList: LiveData<List<TierRow>> get() = _tierList

    // Initialize or fetch the tier list data
    init {
        _tierList.value = createDummyTierList()
    }

    // Function to create a dummy tier list
    private fun createDummyTierList(): List<TierRow> {
        val titles = listOf("S", "A", "B", "C", "D", "E", "F")
        val colors = listOf(Color.RED, Color.parseColor("#FFA500"), Color.YELLOW, Color.GREEN, Color.BLUE, Color.parseColor("#4B0082"), Color.parseColor("#9400D3"))

        return titles.mapIndexed { index, title ->
            TierRow(title, index, colors[index], emptyList())
        }
    }

    fun addImageToTierRow(position: Int, imageUri: String) {
        val currentList = _tierList.value.orEmpty().toMutableList()
        if (position >= 0 && position < currentList.size) {
            currentList[position] = currentList[position].copy(imageUris = currentList[position].imageUris + imageUri)
            _tierList.value = currentList
        }
    }
}