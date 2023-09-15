package com.udeldev.storyapp.helper.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udeldev.storyapp.helper.TokenPreference
import com.udeldev.storyapp.view.detail.DetailViewModel
import com.udeldev.storyapp.view.login.LoginViewModel
import com.udeldev.storyapp.view.main.MainViewModel

class TokenFactory (private val pref :TokenPreference) :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(pref) as T
        }else if (modelClass.isAssignableFrom(DetailViewModel::class.java)){
            return DetailViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}