package com.marsphoto.data.repository

import com.marsphoto.data.remote.api.MarsPhotoApi
import com.marsphoto.data.remote.model.MarsPhotoResponse
import com.marsphoto.domain.repsitory.MarsPhotoRepository
import com.marsphoto.network.ApiResult
import com.marsphoto.network.apiErrorHandler
import javax.inject.Inject

class MarsPhotoRepositoryImpl @Inject constructor(
    private val marsPhotoApi: MarsPhotoApi
): MarsPhotoRepository {
    override suspend fun getMarsPhotos(): ApiResult<List<MarsPhotoResponse>> {
        return apiErrorHandler {
            val response = marsPhotoApi.getPhotos()
            response
        }
    }
}