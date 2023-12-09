package com.cpp.tierbuilder
//
//import android.content.Context
//import androidx.room.Room
//import com.cpp.tierbuilder.database.TierListDatabase
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.flow.Flow
//
//        database.tierListDao().deleteTierList(getTierList(id))
//    }
//
//    companion object {
//        private var INSTANCE: TierListRepository? = null
//
//        fun initialize(context: Context) {
//            if (INSTANCE == null) {
//                INSTANCE = TierListRepository(context)
//            }
//        }
//
//        fun get(): TierListRepository {
//            return INSTANCE ?:
//            throw IllegalStateException("TierListRepository must be initialized")
//        }
//    }
//}