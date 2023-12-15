package com.cpp.tierbuilder

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeScreenViewModel: ViewModel() {
    private val tierListRepository = TierListRepository.get()

    private val _tierLists: MutableStateFlow<List<TierList>> = MutableStateFlow(emptyList())
    val tierLists: StateFlow<List<TierList>>
        get() = _tierLists.asStateFlow()

    suspend fun addTierList(tierList: TierList) {
        tierListRepository.addTierList(tierList)
        Log.d("HomeScreenViewModel", "New Tier List: ${ tierListRepository.getTierList(tierList.id)}")
    }
}