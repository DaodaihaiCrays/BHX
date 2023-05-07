package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("firebase_uid")
    @Expose()
    val firebaseUid: String,

    @SerializedName("email")
    @Expose()
    val email: String? = null,

    @SerializedName("fullname")
    @Expose()
    val fullname: String? = null,

    @SerializedName("phone_number")
    @Expose()
    val phoneNumber: String? = null,

    @SerializedName("province_id")
    @Expose()
    val provinceId: Int? = null,

    @SerializedName("district_id")
    @Expose()
    val districtId: Int? = null,

    @SerializedName("ward_id")
    @Expose()
    val ward: Int? = null,

    @SerializedName("address")
    @Expose()
    val address: String? = null,

    @SerializedName("gender")
    @Expose()
    val gender: String? = null,

    @SerializedName("authority")
    @Expose()
    val authority: String = "CUSTOMER"
)