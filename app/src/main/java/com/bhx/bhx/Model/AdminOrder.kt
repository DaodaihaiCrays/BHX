package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdminOrder(
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("user_id")
    @Expose()
    val user_id: Int,

    @SerializedName("fullname")
    @Expose()
    val fullname: String?,

    @SerializedName("phone")
    @Expose()
    val phone: String?,

    @SerializedName("email")
    @Expose()
    val email: String?,

    @SerializedName("gender")
    @Expose()
    val gender: String,

    @SerializedName("address")
    @Expose()
    val address: String?,

    @SerializedName("delivery_date")
    @Expose()
    val delivery_date: String,

    @SerializedName("payment_method_name")
    @Expose()
    val payment_method_name: String,

    @SerializedName("total_price")
    @Expose()
    val total_price: Double,

    @SerializedName("status")
    @Expose()
    val status: String,

    @SerializedName("is_paid")
    @Expose()
    val is_paid: Boolean,

    @SerializedName("order_items")
    @Expose()
    val order_items: List<AdminOrderItem>?
)

data class AdminOrderItem(
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("quantity")
    @Expose()
    val quantity: Int,

    @SerializedName("product_id")
    @Expose()
    val product_id: Int,

    @SerializedName("name")
    @Expose()
    val name: String?,

    @SerializedName("unit_price")
    @Expose()
    val unit_price: Int?,

    @SerializedName("sale_percent")
    @Expose()
    val sale_percent: Int?,

    @SerializedName("general_description")
    @Expose()
    val general_description: String?,
)

data class AdminOrderUpdateStatus (
    @SerializedName("is_paid")
    @Expose()
    val is_paid: Boolean?,

    @SerializedName("status")
    @Expose()
    val status: Boolean,
)