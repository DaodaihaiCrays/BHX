package com.bhx.bhx.Model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OrderItem (
    @SerializedName("product")
    val product: Product,
    @SerializedName("quantity")
    val quantity: Int
)