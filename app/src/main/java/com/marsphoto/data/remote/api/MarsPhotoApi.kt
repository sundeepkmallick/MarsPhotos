package com.marsphoto.data.remote.api

import com.marsphoto.data.remote.model.MarsPhotoResponse
import retrofit2.http.GET

interface MarsPhotoApi {
    @GET("photos")
    suspend fun getPhotos(): List<MarsPhotoResponse>
}