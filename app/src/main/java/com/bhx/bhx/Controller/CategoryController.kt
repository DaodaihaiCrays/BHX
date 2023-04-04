package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Product
import com.bhx.bhx.Model.ReviewCategory
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface CategoryController {

    @GET("categories/preview-products")
    fun getCategoryProduct(): Call<List<ReviewCategory>>

    @GET("products")
    fun getAllProductsOfCate(@Query("categoryId") categoryId: Int): Call<List<Product>>
}