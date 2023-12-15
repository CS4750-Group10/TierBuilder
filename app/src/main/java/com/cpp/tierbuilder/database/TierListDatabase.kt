package com.cpp.tierbuilder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cpp.tierbuilder.TierList

@Database(entities = [ TierList::class ], version=2)
@TypeConverters(TierListTypeConverters::class)
abstract class TierListDatabase : RoomDatabase() {
    abstract fun tierListDao() : TierListDao
}

val migration_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE TierList ADD COLUMN isSelected INTEGER NOT NULL DEFAULT 0"
        )
    }
}