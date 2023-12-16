package com.capstone.miemo.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.capstone.miemo.data.local.database.MemoDao
import com.capstone.miemo.data.local.database.MemoRoomDatabase
import com.capstone.miemo.data.local.entity.Memo
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MemoRepository(application: Application) {
    private val mMemoDao: MemoDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MemoRoomDatabase.getDatabase(application)
        mMemoDao = db.memoDao()
    }

    fun getAllMemo(): LiveData<List<Memo>> = mMemoDao.getAllMemo()

    fun getMemoByDate(date: String): LiveData<Memo>{
        return mMemoDao.getMemoByDate(date)
    }

    fun insert(memo: Memo){
        mMemoDao.insert(memo)
    }

    fun delete(memo: Memo){
        mMemoDao.delete(memo)
    }

}