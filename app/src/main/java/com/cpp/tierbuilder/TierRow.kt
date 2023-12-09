package com.cpp.tierbuilder

data class TierRow(
    val title: String,
    val position: Int,
    val color: Int,
    val imageUris: List<String> = emptyList()
)