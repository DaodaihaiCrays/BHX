package com.bhx.bhx.Controller

import com.bhx.bhx.Model.AdminProduct
import com.bhx.bhx.Model.AdminProductStatus
import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.Promotion
import retrofit2.Call
import retrofit2.http.*

interface ProductAdminController {
    @GET("admin/products")
    fun findAll(): Call<List<AdminProduct>>

    @GET("admin/products")
    fun search(@Query("keyword") keyword: String): Call<List<AdminProduct>>

    @POST("admin/products")
    fun create(@Body product: AdminProduct): Call<AdminProduct>

    @PUT("admin/products/{id}")
    fun update(@Path("id") id: Int, @Body product: AdminProduct): Call<AdminProduct>

    @PUT("admin/products/{id}/status")
    fun updateStatus(@Path("id") id: Int, @Body productStatus: AdminProductStatus): Call<AdminProduct>
}