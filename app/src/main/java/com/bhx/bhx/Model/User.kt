package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class User (
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("firebase_uid")
    val firebase_uid: String,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("fullname")
    val fullname: String? = null,

    @SerializedName("phone_number")
    val phoneNumber: String? = null,

    @SerializedName("province_id")
    val provinceId: Int? = null,

    @SerializedName("district_id")
    val districtId: Int? = null,

    @SerializedName("ward_id")
    val wardId: Int? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("authority")
    val authority: String = "CUSTOMER",

    @SerializedName("created")
    @Expose()
    val created: String? = null,

    @SerializedName("modified")
    @Expose()
    val modified: String? = null
)