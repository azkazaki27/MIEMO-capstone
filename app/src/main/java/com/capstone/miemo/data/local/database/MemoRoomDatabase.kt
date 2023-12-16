package com.capstone.miemo.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.capstone.miemo.data.local.entity.Memo

@Database(entities = [Memo::class], version = 1, exportSchema = false)
abstract class MemoRoomDatabase : RoomDatabase(){
    abstract fun memoDao() : MemoDao

    companion object{
        @Volatile
        private var INSTANCE: MemoRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MemoRoomDatabase{
            if(INSTANCE == null){
                synchronized(MemoRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MemoRoomDatabase::class.java, "memo_database")
                        .build()
                }
            }
            return INSTANCE as MemoRoomDatabase
        }
    }
}