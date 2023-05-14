package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AdminProductStatus(
    @SerializedName("active")
    @Expose()
    val active: Boolean,
)
