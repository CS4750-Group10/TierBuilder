package com.cpp.tierbuilder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class SavedListsViewModel : ViewModel() {
    private val tierListRepository = TierListRepository.get()

    private val _tierLists: MutableStateFlow<List<TierList>> = MutableStateFlow(emptyList())
    val tierLists: StateFlow<List<TierList>>
    get() = _tierLists.asStateFlow()

    init {
        viewModelScope.launch {
            tierListRepository.getTierLists().collect() {
                _tierLists.value = it
            }
        }
    }
    // Create a copy of a tier list with a new UUID and title
    suspend fun copyTierList(tierListId: UUID) {
        val oldTierList = tierListRepository.getTierList(tierListId)
        val copy = oldTierList.copy(
            id = UUID.randomUUID(),
            title = "Copy of ${oldTierList.title}",
            isSelected = false
        )
        tierListRepository.addTierList(copy)
    }

    // Delete a tier list from database
    suspend fun deleteTierList(tierListId: UUID) {
        tierListRepository.deleteTierList(tierListId)
    }

    // Functions for selected items in RecyclerView
    suspend fun toggleSelection(tierListId: UUID) {
        val tierList = tierListRepository.getTierList(tierListId)
        tierList.isSelected = !tierList.isSelected
        tierListRepository.updateTierList(tierList)
    }

    fun clearSelection() {
        val mutableTierLists = _tierLists.value.toMutableList()
        for (tierList in mutableTierLists) {
            if (tierList.isSelected) {
                tierList.isSelected = false
                tierListRepository.updateTierList(tierList)
            }
        }
    }

    // Iterate through all selected items
    fun iterateSelectedItems(action: (TierList) -> Unit) {
        viewModelScope.launch {
            val tierLists = _tierLists.value
            for (tierList in tierLists) {
                if (tierList.isSelected) {
                    action.invoke(tierList)
                }
            }
        }
    }
}
