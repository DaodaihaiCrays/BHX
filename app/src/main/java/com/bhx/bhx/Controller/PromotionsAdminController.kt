package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Promotion
import retrofit2.Call
import retrofit2.http.*

interface PromotionsAdminController {
    @GET("admin/promotions")
    fun findAll(): Call<List<Promotion>>

    @GET("admin/promotions/active")
    fun findActive(): Call<List<Promotion>>

    @GET("admin/promotions")
    fun search(@Query("keyword") keyword: String): Call<List<Promotion>>

    @POST("admin/promotions")
    fun create(@Body promotion: Promotion): Call<Promotion>

    @PUT("admin/promotions/{id}")
    fun update(@Path("id") id: Int, @Body promotion: Promotion): Call<Promotion>

    @DELETE("admin/promotions/{id}")
    fun delete(@Path("id") id: Int): Call<Void>
}