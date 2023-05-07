package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User (
    @SerializedName("id")
    val id: Int,

    @SerializedName("email")
    val email: String,

    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("phone_number")
    val phoneNumber: String,

    @SerializedName("province_id")
    val provinceId: Int,

    @SerializedName("district_id")
    val districtId: Int, 

    @SerializedName("ward_id")
    val wardId: Int, 

    @SerializedName("address")
    val address: String, 

    @SerializedName("gender")
    val gender: String,

    @SerializedName("authority")
    val authority: String,

    @SerializedName("created")
    @Expose()
    val created: String,

    @SerializedName("modified")
    @Expose()
    val modified: String
)