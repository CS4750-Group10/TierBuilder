package com.cpp.tierbuilder.database

import androidx.room.TypeConverter
import com.cpp.tierbuilder.TierRow

class TierListTypeConverters {
    @TypeConverter
    fun fromRowList(rowList: List<TierRow>): String {
        return rowList.joinToString(separator = " | ")
    }

    @TypeConverter
    fun toRowList(rowListString: String): List<TierRow> {
        val rowList = mutableListOf<TierRow>()

        for (rowString in rowListString.split(" | ")) {
            val tierRow = rowString
                .substringAfter("(")
                .substringBefore(")")
                .split("; ")

            // Lance made change her in line with the mutable images property
            rowList.add(TierRow(tierRow[0], tierRow[1].toInt(), tierRow[2], toStringList(tierRow[3]).toMutableList(), tierRow[4].toBoolean()))
        }
        return rowList.toList()
    }

    @TypeConverter
    fun fromStringList(stringList: List<String>): String {
        return stringList.toString()
    }
    @TypeConverter
    fun toStringList(listString: String): List<String> {
        val stringList = listString
            .substringAfter("[")
            .substringBefore("]")
            .split(", ")
        return stringList
    }
}

