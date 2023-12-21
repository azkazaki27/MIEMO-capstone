package com.capstone.miemo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.entity.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: MemoRepository): ViewModel() {

    fun getSession(): LiveData<User> {
        return repository.getSession().asLiveData()
    }

    fun updateUsername(userId: String, newName: String) = repository.updateUsername(userId, newName)

    fun logOut(){
        viewModelScope.launch{
            repository.logOut()
        }
    }
}