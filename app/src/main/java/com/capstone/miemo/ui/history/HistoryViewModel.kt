package com.capstone.miemo.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo

class HistoryViewModel(private val repository: MemoRepository,private val pref: AppPreferences) : ViewModel() {

    // LiveData for memo data
    val allMemo: LiveData<List<Memo>> = repository.getAllMemo()

    // You can add other functions related to history here

}


