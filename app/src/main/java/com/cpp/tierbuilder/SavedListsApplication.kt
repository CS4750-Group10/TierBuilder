package com.cpp.tierbuilder

import android.app.Application

class SavedListsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TierListRepository.initialize(this)
    }
}