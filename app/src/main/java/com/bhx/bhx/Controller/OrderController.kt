package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.Timerange
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.GET
import retrofit2.http.POST
import java.sql.Time

interface OrderController {
    @GET("/users/{uid}/orders")
    fun getOrderList(@Path("uid") uid: String): Call<List<Order>>

    @GET("/users/{uid}/orders/time-range")
    fun getTimeRange(@Path("uid") uid: String): Call<List<Timerange>>

    @POST("/users/{uid}/orders")
    fun newOrder(@Path("uid") uid: String, @Body requestBody: RequestBody):  Call<ResponseBody>
}