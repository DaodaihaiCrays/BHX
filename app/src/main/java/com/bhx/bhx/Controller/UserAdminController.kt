package com.bhx.bhx.Controller

import com.bhx.bhx.Model.AdminUser
import com.bhx.bhx.Model.Promotion
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserAdminController {
    @GET("admin/users")
    fun findAll(): Call<List<AdminUser>>

    @GET("admin/users")
    fun search(@Query("keyword") keyword: String): Call<List<AdminUser>>
}