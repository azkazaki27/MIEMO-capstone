package com.capstone.miemo.data.remote.retrofit


import com.capstone.miemo.data.remote.response.AuthUser
import com.capstone.miemo.data.remote.response.BaseResponse
import com.capstone.miemo.data.remote.response.LoginRequest
import com.capstone.miemo.data.remote.response.LoginResponse
import com.capstone.miemo.data.remote.response.RegisterRequest
import com.capstone.miemo.data.remote.response.RegisterResponse
import com.capstone.miemo.data.remote.response.SubmitRequest
import com.capstone.miemo.data.remote.response.UpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {
    // AUTH

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<BaseResponse>

    //POST
    @POST("/submit-text")
    fun submitMemo(@Body submitRequest: SubmitRequest):Call<BaseResponse>

    //UPDATE USERNAME
    @PUT("/update-username")
    fun updateUsername(@Body updateUsername: UpdateRequest): Call<BaseResponse>
}
