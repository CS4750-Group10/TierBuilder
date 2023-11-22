package com.cpp.tierbuilder.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.cpp.tierbuilder.TierList
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TierListDao {
    @Query("SELECT * FROM tierList")
    fun getTierLists(): Flow<List<TierList>>

    @Query("SELECT * FROM tierList WHERE id=(:id)")
    suspend fun getTierList(id: UUID): TierList

    @Insert
    suspend fun addTierList(tierList: TierList)

    @Update
    suspend fun updateTierList(tierList: TierList)

    @Delete
    suspend fun deleteTierList(tierList: TierList)
}