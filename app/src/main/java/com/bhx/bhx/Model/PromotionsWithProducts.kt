package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PromotionsWithProducts(
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("sale_percent")
    @Expose()
    val sale_percent: Int,

    @SerializedName("start_time")
    @Expose()
    val start_time: String,

    @SerializedName("end_time")
    @Expose()
    val end_time: String,

    @SerializedName("day_left")
    @Expose()
    val day_left: Int,

    @SerializedName("products")
    @Expose()
    val products: MutableList<Product>
)