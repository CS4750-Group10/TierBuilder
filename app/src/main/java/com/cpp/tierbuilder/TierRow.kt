package com.cpp.tierbuilder

data class TierRow(
    var title: String,
    val row: Int,
    val color: String,
    var images: MutableList<String> = mutableListOf(),
    var isEditing: Boolean = false
)
 {
    override fun toString(): String {
        return "TierRow($title; $row; $color; $images; $isEditing)"
    }
}