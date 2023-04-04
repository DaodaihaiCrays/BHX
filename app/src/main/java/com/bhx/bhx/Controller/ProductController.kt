package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductController {
    @GET("products/{id}")
    fun getDetailProduct(@Path("id") id: Int) : Call<Product>
}