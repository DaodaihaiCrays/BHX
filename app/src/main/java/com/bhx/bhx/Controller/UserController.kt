package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.User
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.PUT

interface UserController {
    @GET("users/{uid}")
    fun getUserInfo(@Path("uid") uid: String): Call<User>

    @PUT("users/{uid}")
    fun updateUserInfo(@Path("uid") uid: String, @Body requestBody: RequestBody) :Call<ResponseBody>
}