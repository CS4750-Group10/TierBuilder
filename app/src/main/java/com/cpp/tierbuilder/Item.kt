package com.cpp.tierbuilder

import android.net.Uri

// this class is intended to represent a single image within a single tier
data class Item(
    val name: String,
    val imagePath: Uri
)
