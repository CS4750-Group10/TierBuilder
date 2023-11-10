package com.cpp.tierbuilder

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class TierList(
    @PrimaryKey val id: UUID,
    val name: String,
    val tierRowList: List<TierRow>,
    val pendingList: List<Item>
)
