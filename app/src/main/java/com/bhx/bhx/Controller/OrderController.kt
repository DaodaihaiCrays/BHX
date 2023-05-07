package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import retrofit2.Call
import retrofit2.http.Path
import retrofit2.http.GET

interface OrderController {
    @GET("/users/{uid}/orders")
    fun getOrderList(@Path("uid") uid: String): Call<List<Order>>
}