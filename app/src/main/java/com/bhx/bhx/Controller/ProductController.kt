package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductController {
    @GET("products/{id}")
    fun getDetailProduct(@Path("id") id: Int) : Call<Product>

    @GET("products")
    fun getAllProductsOfSearch(@Query("keyword") keyword: String): Call<List<Product>>

}