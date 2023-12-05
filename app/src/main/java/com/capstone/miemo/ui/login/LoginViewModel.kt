package com.capstone.miemo.ui.activity.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.capstone.miemo.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: AppRepository,) : ViewModel() {
    private val loginInput = MutableLiveData<Pair<String, String>>()
    val loginStatus = loginInput.switchMap {
        repository.login(it.first, it.second)
  }
    fun login(email: String, password: String) {
        loginInput.value = Pair(email, password)
    }
    fun inputIsValid(binding: ActivityLoginBinding): Boolean {
        val noError = (binding.edEmail.error == null) && (binding.edPassword.error == null)
        val nonZeroLength = (binding.edEmail.text.toString()
            .isNotEmpty())
                && (binding.edPassword.text.toString()
            .isNotEmpty())
        return noError && nonZeroLength
    }
    fun addUser(user: UserModel) = viewModelScope.launch { repository.addUserData(user) }
    fun holdToken(token: String) = repository.holdToken(token)
}