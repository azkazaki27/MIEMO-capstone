package com.capstone.miemo.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.capstone.miemo.data.local.database.MemoDao
import com.capstone.miemo.data.local.database.MemoRoomDatabase
import com.capstone.miemo.data.local.entity.Memo
import com.capstone.miemo.data.local.entity.User
import com.capstone.miemo.data.remote.response.BaseResponse
import com.capstone.miemo.data.remote.response.SubmitRequest
import com.capstone.miemo.data.remote.response.UpdateRequest
import com.capstone.miemo.data.remote.retrofit.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MemoRepository(
    private val database: MemoRoomDatabase,
    private val apiService: ApiService,
    private val preferences: AppPreferences) {

    fun getAllMemo(): LiveData<List<Memo>> = database.memoDao().getAllMemo()

    fun getMemoByDate(date: String): LiveData<Memo>{
        return database.memoDao().getMemoByDate(date)
    }

    fun insert(memo: Memo){
        database.memoDao().insert(memo)
    }

    fun delete(memo: Memo){
        database.memoDao().delete(memo)
    }

    fun submitText(userId: String, text: String): LiveData<Result<String>>{
        val result = MediatorLiveData<Result<String>>()
        result.value = Result.Loading
        val submitRequest = SubmitRequest(userId, text)
        val client = apiService.submitMemo(submitRequest)
        client.enqueue(object : Callback<BaseResponse>{
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

    fun updateUsername(userId: String, newName: String): LiveData<Result<String>>{
        val result = MediatorLiveData<Result<String>>()
        result.value = Result.Loading
        val updateRequest = UpdateRequest(userId, newName)
        val client = apiService.updateUsername(updateRequest)
        client.enqueue(object : Callback<BaseResponse>{
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

    fun getSession(): Flow<User> {
        return preferences.getSession()
    }

    suspend fun logOut(){
        preferences.logOut()
    }

    companion object{
        @Volatile
        private var instance: MemoRepository? = null

        fun getInstance(database: MemoRoomDatabase, apiService: ApiService, preferences: AppPreferences) : MemoRepository =
            instance ?:MemoRepository(database, apiService, preferences)
    }

}