package com.udeldev.storyapp.view.main

import android.util.Log
import androidx.lifecycle.*
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.model.response.AllStoryResponse
import com.udeldev.storyapp.provider.config.ConfigData
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val pref : TokenPreference) :ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allStoryResponse = MutableLiveData<AllStoryResponse>()
    val allStoryResponse : LiveData<AllStoryResponse> = _allStoryResponse

    fun getSession() :LiveData<String> {
        return pref.getToken().asLiveData()
    }

    fun logoutSession(){
        viewModelScope.launch {
            pref.logout()
        }
    }

    init {
        getAllDataStory()
    }

    fun getAllDataStory (){
        _isLoading.value = true

        val token = runBlocking { pref.getToken().first() }
        val client = ConfigData.getApiService(token).getAllStory()
        client.enqueue(object : Callback<AllStoryResponse>{
            override fun onResponse(call: Call<AllStoryResponse>, response: Response<AllStoryResponse>) {
                if (response.isSuccessful){
                    _allStoryResponse.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
                _isLoading.value = false
            }

        })

    }

}