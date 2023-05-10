package com.bhx.bhx.Controller

import com.bhx.bhx.Model.Comments
import com.bhx.bhx.Model.Product
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ProductController {
//    @GET("products/{id}")
//    fun getDetailProduct(@Path("id") id: Int) : Call<Product>

    @GET("products/{id}")
    fun getDetailProduct(@Path("id") id: Int, @Query("favorite") favoriteUserId: String): Call<Product>

    @POST("products/{id}/favorite")
    fun postProductFavorite(@Path("id") id: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @DELETE("products/{id}/favorite")
    fun removeProductFavorites(@Path("id") productId: Int, @Query("user_id") userId: String): Call<Void>

    @GET("products")
    fun getAllProductsOfSearch(@Query("keyword") keyword: String): Call<List<Product>>
    @POST("products/{id}/comments")
    fun postComment(@Path("id") id: Int, @Body requestBody: RequestBody): Call<ResponseBody>

    @POST("products/{id}/comments/{c_id}")
    fun postSubComment(@Path("id") id: Int, @Path("c_id") c_id: Int, @Body requestBody: RequestBody): Call<ResponseBody>

//    @GET("products/{id}")
//    fun getProductWithoutRelated(@Path("id") id: Int, @Query("noRelated") noRelated: Boolean = true): Call<Product>
}