package com.marsphoto.data.mapper

import com.marsphoto.common.Mapper
import com.marsphoto.data.remote.model.MarsPhotoResponse
import com.marsphoto.domain.model.MarsPhoto
import javax.inject.Inject

class MarsPhotosMapper @Inject constructor(): Mapper<List<MarsPhotoResponse>, List<MarsPhoto>> {
    override fun map(from: List<MarsPhotoResponse>): List<MarsPhoto> {
        return from.map {
            MarsPhoto(
                id = it.id,
                imgSrc = it.imgSrc
            )
        }
    }
}