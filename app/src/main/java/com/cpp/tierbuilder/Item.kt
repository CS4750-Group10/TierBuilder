package com.cpp.tierbuilder

// A single item to be put into the tier list
data class Item(
    val name: String = "",
    val imageFileName: String? = null
) {
    override fun toString(): String {
        return "Item($name; $imageFileName)"
    }
}
