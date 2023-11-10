package com.cpp.tierbuilder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cpp.tierbuilder.TierList

@Database(entities = [ TierList::class ], version=1)
@TypeConverters(TierListTypeConverters::class)
abstract class TierListDatabase : RoomDatabase() {
    abstract fun tierListDao() : TierListDao
}