package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.User
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.GET

interface UserController {
    @GET("users/{uid}")
    fun getUserInfo(@Path("uid") uid: String): Call<User>
}