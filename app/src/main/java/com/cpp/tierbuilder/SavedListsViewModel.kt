package com.cpp.tierbuilder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavedListsViewModel : ViewModel() {
    private val tierListRepository = TierListRepository.get()

    private val _tierlists: MutableStateFlow<List<TierList>> = MutableStateFlow(emptyList())
    val tierlists: StateFlow<List<TierList>>
        get() = _tierlists.asStateFlow()

    init {
        viewModelScope.launch {
            tierListRepository.getTierLists().collect() {
                _tierlists.value = it
            }
        }
    }

    //Place functions for copy, delete, and load tier lists here
}