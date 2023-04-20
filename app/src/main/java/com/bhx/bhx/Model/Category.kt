package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("id")
    @Expose()
    val id: Int?,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("description")
    @Expose()
    val description: String,
)
