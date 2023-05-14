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
data class Product(
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("name")
    @Expose()
    val name: String,

    @SerializedName("unit_price")
    @Expose()
    val unit_price: Int,

    @SerializedName("stock")
    @Expose()
    val stock: Int,

    @SerializedName("category_id")
    @Expose()
    val category_id: Int,

    @SerializedName("favorite")
    @Expose()
    var favorite: Boolean,

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

    @SerializedName("sale_percent")
    @Expose()
    val sale_percent: Int?,

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

    @SerializedName("related")
    @Expose()
    val related: List<Product>
)

@Serializer(forClass = ZonedDateTime::class)
object ZonedDateTimeSerializer : KSerializer<ZonedDateTime> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): ZonedDateTime {
        return ZonedDateTime.parse(decoder.decodeString())
    }
}