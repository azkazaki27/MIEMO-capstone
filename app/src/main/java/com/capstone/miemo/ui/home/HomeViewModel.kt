package com.capstone.miemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.data.local.entity.User
import java.text.SimpleDateFormat
import java.util.Date

class HomeViewModel(private val repository: MemoRepository) : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun insert(memo: Memo){
        repository.insert(memo)
    }

    fun getSession(): LiveData<User>{
        return repository.getSession().asLiveData()
    }

    fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val date = Date()
        return formatter.format(date)
    }

    fun getMemoByDate(date: String): LiveData<Memo>?{
        return repository.getMemoByDate(date)
    }

    fun getTodayMemo(date: String) = repository.getMemoToday(date)

    fun submitMemo(userId: String, text: String) = repository.submitText(userId, text)
}