package com.cpp.tierbuilder.database

import androidx.room.TypeConverter
import com.cpp.tierbuilder.Item
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
                .replace(rowString.substring(rowString.indexOf("["), rowString.lastIndexOf("]") + 2), "")
                .substringBefore(")")
                .split("; ")

            val itemList = rowString
                .substring(rowString.indexOf("["), rowString.lastIndexOf("]") + 1)

            rowList.add(
                TierRow(
                    tierRow[0],
                    tierRow[1].toInt(),
                    tierRow[2],
                    toItemList(itemList).map { it.imageFileName ?: "" }.toMutableList(),  // Extract image file names
                    tierRow[3].toBoolean()
                )
            )
        }
        return rowList.toMutableList()
    }

    @TypeConverter
    fun fromItemList(itemList: List<Item>): String {
        return itemList.joinToString(separator = ", ") { it.toString() }
    }

    @TypeConverter
    fun toItemList(itemListString: String): List<Item> {
        val itemList = mutableListOf<Item>()

        for (itemString in itemListString.split(", ")) {
            val item = itemString
                .substringAfter("(")
                .substringBefore(")")
                .split("; ")

            itemList.add(Item(item[0], item[1]))
        }

        return itemList.toList()
    }
}

