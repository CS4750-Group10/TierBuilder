package com.cpp.tierbuilder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TierList(
    @PrimaryKey val id: UUID,
    val title: String,
    val tierRowList: List<TierRow> = emptyList(),
    var isSelected: Boolean = false
)
