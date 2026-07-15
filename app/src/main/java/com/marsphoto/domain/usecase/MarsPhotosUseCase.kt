package com.marsphoto.domain.usecase

import com.marsphoto.data.mapper.MarsPhotosMapper
import com.marsphoto.domain.model.MarsPhoto
import com.marsphoto.domain.repsitory.MarsPhotoRepository
import com.marsphoto.network.ApiResult
import javax.inject.Inject

class MarsPhotosUseCase @Inject constructor(
    private val marsPhotoRepository: MarsPhotoRepository,
    private val marsPhotosMapper: MarsPhotosMapper
) {
    suspend fun getPhotos(): ApiResult<List<MarsPhoto>> {
        return when (val result = marsPhotoRepository.getMarsPhotos()){
            is ApiResult.Success -> ApiResult.Success(data = marsPhotosMapper.map(result.data))
            is ApiResult.Failure -> ApiResult.Failure(result.error)
        }
    }
}