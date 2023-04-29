package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubCategory (
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("img")
    @Expose()
    val img: String,

)