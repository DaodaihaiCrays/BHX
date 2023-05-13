package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class AdminUser(
    @SerializedName("firebase_uid")
    @Expose()
    val firebase_uid: String,

    @SerializedName("email")
    @Expose()
    val email: String?,

    @SerializedName("fullname")
    @Expose()
    val fullname: String?,

    @SerializedName("phone_number")
    @Expose()
    val phone_number: String?,

    @SerializedName("address")
    @Expose()
    val address: String?,

    @SerializedName("gender")
    @Expose()
    var gender: String?,

    @SerializedName("authority")
    @Expose()
    val authority: String,

)
