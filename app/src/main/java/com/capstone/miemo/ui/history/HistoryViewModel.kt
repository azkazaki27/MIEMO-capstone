package com.capstone.miemo.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo

class HistoryViewModel(private val memoRepository: MemoRepository) : ViewModel() {

    // LiveData for memo data
    val allMemo: LiveData<List<Memo>> = memoRepository.getAllMemo()

    // You can add other functions related to history here
}
