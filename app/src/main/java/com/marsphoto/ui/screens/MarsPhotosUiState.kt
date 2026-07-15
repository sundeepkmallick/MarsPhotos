package com.marsphoto.ui.screens

import com.marsphoto.common.UiText
import com.marsphoto.domain.model.MarsPhoto

sealed class MarsPhotosUiState {
    data class Success(val marsPhotos: List<MarsPhoto>): MarsPhotosUiState()
    data class Error( val message: UiText): MarsPhotosUiState()
    object Loading: MarsPhotosUiState()
}