package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Category
import retrofit2.Call
import retrofit2.http.*

interface CategoryAdminController {
    @GET("admin/categories")
    fun findAll(): Call<List<Category>>

    @GET("admin/categories")
    fun findCateOfSearch(@Query("keyword") keyword: String): Call<List<Category>>

    @POST("admin/categories")
    fun create(@Body category: Category): Call<Category>

    @PUT("admin/categories/{id}")
    fun update(@Path("id") id: Int, @Body category: Category): Call<Category>

    @DELETE("admin/categories/{id}")
    fun delete(@Path("id") id: Int): Call<Void>
}