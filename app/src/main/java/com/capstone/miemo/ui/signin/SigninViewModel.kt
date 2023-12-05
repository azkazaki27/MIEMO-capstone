package com.capstone.miemo.ui.activity.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.capstone.miemo.databinding.ActivitySigninBinding

class SigninViewModel(private val repository: AppRepository,) : ViewModel()
{
    private val registerInput = MutableLiveData<Triple<String, String, String>>()
    fun register(name: String, email: String, password: String) {
        registerInput.value = Triple(name, email, password)
    }
    val registerStatus = registerInput.switchMap {
        repository.register(it.first, it.second, it.third)
    }

    fun inputIsValid(binding: ActivitySigninBinding): Boolean {
        val noError = (binding.edRegisterName.error == null) && (binding.edEmail.error == null) && (binding.edPassword.error == null)
        val nonZeroLength = (binding.edRegisterName.text.toString()
            .isNotEmpty()) && (binding.edEmail.text.toString()
            .isNotEmpty()) && (binding.edPassword.text.toString()
            .isNotEmpty())
        return noError && nonZeroLength
    }
}