package com.cpp.tierbuilder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class TierListViewModel(tierListId: UUID): ViewModel() {

    private val tierListRepository = TierListRepository.get()

    private val _tierList: MutableStateFlow<TierList?> = MutableStateFlow(null)
    val tierList: StateFlow<TierList?> = _tierList.asStateFlow()

    init {
        viewModelScope.launch {
            _tierList.value = tierListRepository.getTierList(tierListId)
        }
    }

    // Add tier list to database
    suspend fun addTierList(tierList: TierList) {
        tierListRepository.addTierList(tierList)
    }

    // Update saved private tierlist
    fun updateTierList(onUpdate: (TierList) -> TierList) {
        _tierList.update { oldTierList ->
            oldTierList?.let { onUpdate(it) }
        }
    }

    // Clear public tierlist
    override fun onCleared() {
        super.onCleared()
        tierList.value?.let { tierListRepository.updateTierList(it) }
    }
}

class TierListViewModelFactory(
    private val tierListId: UUID
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TierListViewModel(tierListId) as T
    }
}