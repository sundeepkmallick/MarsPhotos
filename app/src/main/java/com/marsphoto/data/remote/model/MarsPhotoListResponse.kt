package com.marsphoto.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MarsPhotoResponse(
    val id: String,
    @Json(name = "img_src")
    val imgSrc: String
)