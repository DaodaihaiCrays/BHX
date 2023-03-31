package com.bhx.bhx.Controller

import com.bhx.bhx.Model.ReviewCategory
import retrofit2.Call
import retrofit2.http.GET


interface CategoryController {
    @GET("/categories/preview-products")
    fun getCategoryProduct(): Call<List<ReviewCategory>>
}