package com.bhx.bhx.Model

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.KSerializer
import java.sql.Timestamp
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.ZonedDateTime

@Serializable
data class Order (
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("items")
    val list: List<OrderItem>,
//=== replace by data class ===
    @SerializedName("user_id")
    val user_id: String,
//===
    @SerializedName("fullname")
    val fullname: String,

    @SerializedName("phone")
    val phone: String,

    @SerializedName("email")
    val email: String,

    @SerializedName("gender")
    val gender: String,
//=== replace by data class ===
    @SerializedName("province_id")
    val province: String,

    @SerializedName("district_id")
    val district: String,

    @SerializedName("ward_id")
    val ward: String,
//===
    @SerializedName("address")
    val address: String,
//=== replace by data class ===
    @SerializedName("delivery_date")
    val deliveryDate: String,

    @SerializedName("delivery_timerange_id")
    val deliveryTimerange: String,

    @SerializedName("payment_method_id")
    val paymentMethod: String,

    @SerializedName("total_price")
    val totalPrice: Int,

    @SerializedName("id_paid")
    val isPaid: Boolean,

    @SerializedName("status")
    val status: String
//===
)