package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Category
import retrofit2.Call
import retrofit2.http.*

interface CategoryAdminController {
    @GET("admin/categories")
    fun findAll(): Call<List<Category>>

    @POST("admin/categories")
    fun create(@Body category: Category): Call<Category>

    @POST("admin/categories/{id}")
    fun update(@Path("id") id: Int, @Body category: Category): Call<Category>
}