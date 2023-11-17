package com.cpp.tierbuilder

data class TierRow(
    var title: String,
    val row: Int,
    val color: String,
    val items: List<Item>,
    var isEditing: Boolean = false
)
