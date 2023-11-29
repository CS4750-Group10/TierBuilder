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

    private val _tierlists: MutableStateFlow<List<TierList>> = MutableStateFlow(emptyList())
    val tierlists: StateFlow<List<TierList>>
        get() = _tierlists.asStateFlow()

    private var _selectedLists = mutableListOf<UUID>()
    val selectedLists: List<UUID>
        get() = _selectedLists.toList()

    init {
        viewModelScope.launch {
            tierListRepository.getTierLists().collect() {
                _tierlists.value = it
            }
        }
    }
    // Create a copy of a tier list with a new UUID and title
    suspend fun copyTierList(tierListId: UUID) {
        val oldTierList = tierListRepository.getTierList(tierListId)
        val copy = oldTierList.copy(
            id = UUID.randomUUID(),
            title = "Copy of ${oldTierList.title}"
        )
        tierListRepository.addTierList(copy)
    }

    // Delete a tier list from database
    suspend fun deleteTierList(tierListId: UUID) {
        tierListRepository.deleteTierList(tierListId)
    }

    // Functions for selected items in RecyclerView
    fun toggleSelection(tierListId: UUID) {
        if (_selectedLists.contains(tierListId)) {
            _selectedLists.remove(tierListId)
        } else {
            _selectedLists.add(tierListId)
        }
    }

    fun clearSelections() {
        _selectedLists.clear()
    }
}