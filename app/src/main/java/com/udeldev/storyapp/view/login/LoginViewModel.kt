package com.udeldev.storyapp.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.model.response.LoginResponse
import com.udeldev.storyapp.provider.config.ConfigAuth
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: TokenPreference) : ViewModel() {

    companion object {
        private const val TAG = "LoginViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError : LiveData<Boolean> = _isError

    init {
        _isLoading.value = false
    }

    fun loginUser(email: String, password: String) {
        _isLoading.value = true
        val client = ConfigAuth.getApiService().loginAuth(email, password)
        client.enqueue(
            object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        Log.i(TAG, "Login Success")
                        val token = response.body()?.loginResult?.token.toString()
                        viewModelScope.launch {
                            pref.setToken(token)
                        }
                    } else {
                        _isError.value = true
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    _isError.value = true
                    _isLoading.value = false
                }

            }
        )
    }
}