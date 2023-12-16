package com.capstone.miemo.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.miemo.data.local.database.MemoDao
import com.capstone.miemo.data.local.database.MemoRoomDatabase
import com.capstone.miemo.data.local.entity.Memo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MemoRepository(private val database: MemoRoomDatabase) {

    fun getAllMemo(): LiveData<List<Memo>> = database.memoDao().getAllMemo()

    fun getMemoByDate(date: String): LiveData<Memo>{
        return database.memoDao().getMemoByDate(date)
    }

    fun insert(memo: Memo){
        database.memoDao().insert(memo)
    }

    fun delete(memo: Memo){
        database.memoDao().delete(memo)
    }

    companion object{
        @Volatile
        private var instance: MemoRepository? = null

        fun getInstance(database: MemoRoomDatabase) : MemoRepository =
            instance ?:MemoRepository(database)
    }

}