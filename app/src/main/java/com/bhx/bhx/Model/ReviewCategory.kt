package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class ReviewCategory (

    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("countProducts")
    @Expose()
    val countProducts: Int,

    @SerializedName("products")
    @Expose()
    val products: MutableList<Product>
)