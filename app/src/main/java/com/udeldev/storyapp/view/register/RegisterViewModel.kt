package com.udeldev.storyapp.view.register

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.model.response.BasicResponse
import com.udeldev.storyapp.model.response.LoginResponse
import com.udeldev.storyapp.provider.config.ConfigAuth
import com.udeldev.storyapp.view.login.LoginViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        _isLoading.value = false
    }


    fun regisUser(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ConfigAuth.getApiService().registerAuth(name, email, password)
        client.enqueue(
            object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    if (response.isSuccessful){
                        _isError.value = false
                    }else{
                        Log.e(TAG, "onFailure: ${response.message()}")
                        _isError.value = true
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    _isLoading.value = false
                }

            }
        )
//        client.enqueue(
//            object : Callback<LoginResponse> {
//                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                    if (response.isSuccessful) {
//                        Log.i(LoginViewModel.TAG, "Login Success")
//                        val token = response.body()?.loginResult?.token.toString()
//                        viewModelScope.launch {
//                            pref.setToken(token)
//                        }
//                    } else {
//                        Log.e(LoginViewModel.TAG, "onFailure: ${response.message()}")
//                    }
//                    _isLoading.value = false
//                }
//
//                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    Log.e(LoginViewModel.TAG, "onFailure: ${t.message.toString()}")
//                    _isLoading.value = false
//                }
//
//            }
//        )
    }


}