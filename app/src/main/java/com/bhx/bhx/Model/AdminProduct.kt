package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdminProduct(
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("unit_price")
    @Expose()
    val unit_price: Double,

    @SerializedName("stock")
    @Expose()
    val stock: Int,

    @SerializedName("category_id")
    @Expose()
    val category_id: Int,

    @SerializedName("general_description")
    @Expose()
    val general_description: String,

    @SerializedName("attribute_label")
    @Expose()
    val attribute_label: List<String>?,

    @SerializedName("attribute_value")
    @Expose()
    val attribute_value: List<String>?,

    @SerializedName("promotion_id")
    @Expose()
    val promotion_id: Int?,

    @SerializedName("note")
    @Expose()
    val note: String?,

    @SerializedName("created")
    @Expose()
    val created: String,

    @SerializedName("modified")
    @Expose()
    val modified: String,

    @SerializedName("banner")
    @Expose()
    val banner: String,

    @SerializedName("status")
    @Expose()
    val status: String,

    @SerializedName("category_name")
    @Expose()
    val category_name: String,
)
