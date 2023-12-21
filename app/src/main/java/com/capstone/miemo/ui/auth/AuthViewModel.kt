package com.capstone.miemo.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.miemo.data.AppPreferences
import com.capstone.miemo.data.AuthRepository
import com.capstone.miemo.data.local.entity.User
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: AppPreferences, private val authRepository: AuthRepository) : ViewModel() {

    fun isLoggedIn(): LiveData<Boolean> = pref.isLoggedIn().asLiveData()

    val userToken = pref.getAuthToken().asLiveData()

    fun saveUser(user: User) {
        viewModelScope.launch {
            pref.saveAuthUser(user)
        }
    }

    fun login(username: String, password: String) = authRepository.login(username, password)

    fun register(email: String, password: String, username: String) =
        authRepository.register(email, password, username)

    fun logout() {
        saveUser(User("", "Guest", ""))
    }
}