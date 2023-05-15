package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import java.time.OffsetTime
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.Serializer
import java.time.format.DateTimeFormatter

@Serializable
data class Timerange (
    @SerializedName("id")
    val id: Int,
    @SerializedName("start_time")
    val startTime: String,
    @SerializedName("end_time")
    val endTime: String,
    @SerializedName("fee")
    val fee: Float
)

@Serializer(forClass = OffsetTime::class)
object OffsetTimeSerializer : KSerializer<OffsetTime> {
    private val formatter = DateTimeFormatter.ofPattern("HH:mm:ssXXX")

    override fun serialize(encoder: Encoder, value: OffsetTime) {
        val str = value.format(formatter)
        encoder.encodeString(str)
    }

    override fun deserialize(decoder: Decoder): OffsetTime {
        val str = decoder.decodeString()
        return OffsetTime.parse(str, formatter)
    }
}