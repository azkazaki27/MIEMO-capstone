package com.capstone.miemo.di

import android.content.Context
import com.capstone.miemo.data.AuthRepository
import com.capstone.miemo.data.MemoRepository
import com.capstone.miemo.data.local.database.MemoRoomDatabase
import com.capstone.miemo.data.remote.retrofit.ApiConfig

object Injection {
    fun provideAuthRepository(context: Context): AuthRepository {
        val apiService = ApiConfig.getApiService()
        return AuthRepository.getInstance(apiService)
    }
}