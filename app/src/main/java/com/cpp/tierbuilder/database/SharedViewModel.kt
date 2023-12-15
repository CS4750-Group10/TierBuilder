package com.cpp.tierbuilder.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cpp.tierbuilder.TierListRepository
import com.cpp.tierbuilder.TierRow
import kotlinx.coroutines.launch

class SharedViewModel : ViewModel() {
    // LiveData for tier rows' images
    private val tierRowsImages = MutableLiveData<List<TierRow>>()

    // LiveData for photo bank images
    private val photoBankImages = MutableLiveData<List<String>>()

    // Functions to update the LiveData
    fun updateTierRowsImages(rows: List<TierRow>) {
        tierRowsImages.value = rows
    }

    fun updatePhotoBankImages(images: List<String>) {
        photoBankImages.value = images
    }

    // Functions to retrieve the LiveData
    fun getTierRowsImages(): LiveData<List<TierRow>> = tierRowsImages
    fun getPhotoBankImages(): LiveData<List<String>> = photoBankImages
}