package com.capstone.miemo.data.remote.retrofit


import com.capstone.miemo.data.remote.response.BaseResponse
import com.capstone.miemo.data.remote.response.LoginRequest
import com.capstone.miemo.data.remote.response.LoginResponse
import com.capstone.miemo.data.remote.response.RegisterRequest
import com.capstone.miemo.data.remote.response.SubmitRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.Multipart
import retrofit2.http.POST

interface ApiService {
    // AUTH

    @POST("/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("/register")
    fun register(@Body registerRequest: RegisterRequest): Call<BaseResponse>

    //POST
    @POST("/submit-text")
    fun submitMemo(@Body submitRequest: SubmitRequest):Call<BaseResponse>
}
