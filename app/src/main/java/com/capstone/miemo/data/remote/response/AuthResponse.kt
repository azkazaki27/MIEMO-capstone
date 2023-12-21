package com.capstone.miemo.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("loginResult")
    val loginResult: AuthUser
)

data class RegisterResponse(
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String,
    @field:SerializedName("loginResult")
    val loginResult: AuthUser
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest  (
    val username: String,
    val email: String,
    val password: String
)

data class SubmitRequest(
    val userId: String,
    val text: String
)

data class AuthUser(
    @field:SerializedName("userId")
    val userId: String,
    @field:SerializedName("user")
    val user: String,
    @field:SerializedName("token")
    val token: String
)