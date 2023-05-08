package com.bhx.bhx.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Comments (
    @SerializedName("id")
    @Expose()
    val id: Int,

    @SerializedName("fullname")
    @Expose()
    val fullname: String,

    @SerializedName("comment_content")
    @Expose()
    val comment_content: String,

    @SerializedName("created")
    @Expose()
    val created: String,

    @SerializedName("replies")
    @Expose()
    val replies: MutableList<Comments>,

    )