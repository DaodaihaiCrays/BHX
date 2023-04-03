package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import retrofit2.Call
import retrofit2.http.GET

interface OrderController {
    @GET("/orders")
    fun getOrderList(): Call<List<Order>>
}