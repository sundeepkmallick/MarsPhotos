package com.marsphoto.domain.repsitory

import com.marsphoto.data.remote.model.MarsPhotoResponse
import com.marsphoto.network.ApiResult

interface MarsPhotoRepository {
    suspend fun getMarsPhotos(): ApiResult<List<MarsPhotoResponse>>
}