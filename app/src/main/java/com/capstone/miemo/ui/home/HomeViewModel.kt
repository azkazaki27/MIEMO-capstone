package com.capstone.miemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo

class HomeViewModel(private val repository: MemoRepository, private val preferences: AppPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun insert(memo: Memo){
        repository.insert(memo)
    }

    val userId = preferences.getUserId().asLiveData()
}