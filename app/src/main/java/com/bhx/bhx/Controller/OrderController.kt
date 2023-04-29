package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import retrofit2.Call
import retrofit2.http.GET

interface OrderController {
    @GET("/users/111/orders")
    fun getOrderList(): Call<List<Order>>
}