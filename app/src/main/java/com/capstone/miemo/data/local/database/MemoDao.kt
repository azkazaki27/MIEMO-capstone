package com.capstone.miemo.data.local.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.miemo.data.local.entity.Memo

@Dao
interface MemoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(memo: Memo)

    @Delete
    fun delete(memo: Memo)

    @Query("SELECT * FROM memo")
    fun getAllMemo(): LiveData<List<Memo>>

    @Query("SELECT * FROM memo WHERE date = :date ORDER BY id DESC")
    fun getMemoByDate(date: String): LiveData<Memo>

    @Query("SELECT * FROM memo WHERE id = :memoId")
    fun getMemoById(memoId: Int): LiveData<Memo>

}