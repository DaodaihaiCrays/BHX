package com.bhx.bhx.Model

import android.os.Build
import androidx.annotation.RequiresApi
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
    val id: Int,
    val name: String,
    val unit_price: Double,
    val stock: Int,
    val category_id: Int,
    val general_description: String,
    val attribute_label: List<String>?,
    val attribute_value: List<String>?,
    val promotion_id: Int?,
    val note: String?,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val created: ZonedDateTime,
    @Serializable(with = ZonedDateTimeSerializer::class)
    val modified: ZonedDateTime,
    val banner: String
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