package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentsController {
    @GET("products/{id}/comments")
    fun getProductComments(@Path("id") id: Int): Call<List<Comments>>
}