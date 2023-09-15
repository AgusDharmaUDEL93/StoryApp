package com.udeldev.storyapp.provider.service

import com.udeldev.storyapp.model.response.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun registerAuth (
        @Field("name") name :String,
        @Field("email") email :String,
        @Field("password") password :String
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginAuth (
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<LoginResponse>


    @GET("stories")
    fun getAllStory ( ) : Call<AllStoryResponse>

    @GET("stories/{id}")
    fun getDetailStory (@Path("id") id :String) : Call<DetailStoryResponse>
}