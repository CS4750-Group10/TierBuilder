package com.cpp.tierbuilder

import android.content.Context
import androidx.room.Room
import com.cpp.tierbuilder.database.TierListDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.util.UUID

private const val DATABASE_NAME = "tierlist-database"
class TierListRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
) {
    private val database: TierListDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TierListDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getTierLists(): Flow<List<TierList>> = database.tierListDao().getTierLists()

    suspend fun getTierList(id: UUID): TierList = database.tierListDao().getTierList(id)

    suspend fun addTierList(tierList: TierList) {
        database.tierListDao().addTierList(tierList)
    }

    fun updateTierList(tierlist: TierList) {
        coroutineScope.launch {
            database.tierListDao().updateTierList(tierlist)
        }
    }

    suspend fun deleteTierList(id: UUID) {
        database.tierListDao().deleteTierList(getTierList(id))
    }

    companion object {
        private var INSTANCE: TierListRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TierListRepository(context)
            }
        }

        fun get(): TierListRepository {
            return INSTANCE ?:
            throw IllegalStateException("TierListRepository must be initialized")
        }
    }
}