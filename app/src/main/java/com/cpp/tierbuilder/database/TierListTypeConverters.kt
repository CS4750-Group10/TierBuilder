package com.cpp.tierbuilder.database

import android.util.Log
import androidx.room.TypeConverter
import com.cpp.tierbuilder.TierRow

class TierListTypeConverters {
    @TypeConverter
    fun fromTierRowList(rowList: List<TierRow>): String {
        return rowList.joinToString(separator = " | ")
    }

    @TypeConverter
    fun toTierRowList(rowListString: String): List<TierRow> {
        Log.d("TierTypeConverters", rowListString)
        val rowList = mutableListOf<TierRow>()

        for (rowString in rowListString.split(" | ")) {
            val tierRow = rowString
                .substringAfter("(")
                .substringBefore(")")
                .split("; ")

            val stringList = tierRow[3]
                .substringAfter("[")
                .substringBefore("]")
                .split(", ")

            // Lance made change her in line with the mutable images property
            rowList.add(TierRow(tierRow[0], tierRow[1].toInt(), tierRow[2], stringList.toMutableList(), tierRow[4].toBoolean()))
        }
        return rowList.toList()
    }
}

