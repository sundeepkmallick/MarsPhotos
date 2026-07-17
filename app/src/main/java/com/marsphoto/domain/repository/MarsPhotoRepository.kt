package com.marsphoto.domain.repository

import com.marsphoto.domain.model.MarsPhoto
import com.marsphoto.network.ApiResult

interface MarsPhotoRepository {
    suspend fun getMarsPhotos(): ApiResult<List<MarsPhoto>>
}