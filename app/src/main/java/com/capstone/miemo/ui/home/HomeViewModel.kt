package com.capstone.miemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel(private val repository: MemoRepository, private val preferences: AppPreferences) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun insert(memo: Memo){
        repository.insert(memo)
    }

    val userId = preferences.getUserId().asLiveData()

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return formatter.format(date)
    }

    fun getMemoByDate(date: String): LiveData<Memo>{
        return repository.getMemoByDate(date)
    }
}