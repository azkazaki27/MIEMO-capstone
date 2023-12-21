package com.capstone.miemo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo

class DetailViewModel(private val repository: MemoRepository) : ViewModel() {
    fun getMemoById(memoId: Int): LiveData<Memo> {
        return repository.getMemoById(memoId)
    }
}