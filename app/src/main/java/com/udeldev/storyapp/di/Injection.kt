//package com.udeldev.storyapp.di
//
//import android.content.Context
//import com.udeldev.storyapp.helper.TokenPreference
//import com.udeldev.storyapp.helper.dataStore
//import com.udeldev.storyapp.provider.config.ConfigData
//import kotlinx.coroutines.flow.first
//import kotlinx.coroutines.runBlocking
//
//object Injection {   fun provideRepository(context: Context): TokenPreference {
//    val pref = TokenPreference.getInstance(context.dataStore)
//    val user = runBlocking { pref.getToken().first() }
//    val apiService = ConfigData.getApiService(user)
//    return TokenPreference.getInstance()
//}
//}
