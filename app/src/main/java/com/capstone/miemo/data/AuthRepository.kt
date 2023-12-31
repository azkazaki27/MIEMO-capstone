package com.capstone.miemo.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.capstone.miemo.data.local.entity.User
import com.capstone.miemo.data.remote.response.AuthUser
import com.capstone.miemo.data.remote.response.BaseResponse
import com.capstone.miemo.data.remote.response.LoginRequest
import com.capstone.miemo.data.remote.response.LoginResponse
import com.capstone.miemo.data.remote.response.RegisterRequest
import com.capstone.miemo.data.remote.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository private constructor(
    private val apiService: ApiService,
) {
    private val loginResult = MediatorLiveData<Result<User>>()

    fun login(email: String, password: String): LiveData<Result<User>> {
        loginResult.value = Result.Loading
        val loginRequest = LoginRequest(email, password)
        val client = apiService.login(loginRequest)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val authUser = loginResponse?.loginResult
                    if (authUser != null) {
                        val user = User(
                            authUser.userId,
                            authUser.user,
                            authUser.token
                        )
                        loginResult.value = Result.Success(user)
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<BaseResponse>() {}.type
                    val baseResponse: BaseResponse? = gson.fromJson(response.errorBody()!!.string(), type)
                    if (baseResponse != null) {
                        loginResult.value = Result.Error(baseResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = Result.Error(t.message.toString())
            }
        })
        return loginResult
    }

    fun register(username: String, email: String, password: String): LiveData<Result<String>> {
        val result = MediatorLiveData<Result<String>>()
        result.value = Result.Loading
        val registerRequest = RegisterRequest(username, email, password)
        val client = apiService.register(registerRequest)
        client.enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    val message = registerResponse?.message
                    if (message != null) {
                        result.value = Result.Success(message)
                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<BaseResponse>() {}.type
                    val baseResponse: BaseResponse? = gson.fromJson(response.errorBody()!!.string(), type)
                    if (baseResponse != null) {
                        result.value = Result.Error(baseResponse.message)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        return result
    }

    companion object {
        @Volatile
        private var instance: AuthRepository? = null
        fun getInstance(
            apiService: ApiService,
            // appExecutors: AppExecutors
        ): AuthRepository =
            instance ?: synchronized(this) {
                instance ?: AuthRepository(apiService)
            }.also { instance = it }
    }
}