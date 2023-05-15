package com.bhx.bhx.Controller

import com.bhx.bhx.Model.AdminOrder
import com.bhx.bhx.Model.Category
import com.bhx.bhx.Model.Order
import com.bhx.bhx.Model.Timerange
import com.bhx.bhx.View.Admin.UpdateStatusOrder
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface OrderAdminController {
    @GET("/admin/orders")
    fun findAll(): Call<List<AdminOrder>>

    @GET("admin/orders")
    fun search(@Query("keyword") keyword: String): Call<List<AdminOrder>>

    @PUT("/admin/orders/{id}")
    fun updateStatus(@Path("id") id: Int, @Body requestBody: UpdateStatusOrder): Call<ResponseBody>

}