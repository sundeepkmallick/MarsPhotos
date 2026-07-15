package com.marsphoto.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marsphoto.R
import com.marsphoto.common.UiText
import com.marsphoto.domain.usecase.MarsPhotosUseCase
import com.marsphoto.internet.InternetObserver
import com.marsphoto.network.ApiError
import com.marsphoto.network.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsPhotosViewModel @Inject constructor(
    private val marsPhotosUseCase: MarsPhotosUseCase,
    private val internetObserver: InternetObserver
): ViewModel(){
    private val _uiState = MutableStateFlow<MarsPhotosUiState>(MarsPhotosUiState.Loading)
    val uiState: StateFlow<MarsPhotosUiState> = _uiState
    private var offlinePreviously = false

    init {
        observeInternetConnection()
        fetchMarsPhotos()
    }

    private fun observeInternetConnection() {
        internetObserver.isConnected.onEach { isConnected ->
            if (!isConnected) {
                offlinePreviously = true
            } else if (offlinePreviously && _uiState.value !is MarsPhotosUiState.Loading) {
                offlinePreviously = false
                fetchMarsPhotos(showLoading = false)
            }
        }.launchIn(viewModelScope)
    }

    fun fetchMarsPhotos(showLoading: Boolean = true){
        viewModelScope.launch {
            if (showLoading){
                _uiState.value = MarsPhotosUiState.Loading
            }

            when(val result = marsPhotosUseCase.getPhotos()){
                is ApiResult.Success -> {
                    _uiState.value = MarsPhotosUiState.Success(result.data)
                }

                is ApiResult.Failure -> {
                    _uiState.value = MarsPhotosUiState.Error(message = result.error.toUiText())
                }
            }
        }
    }

    fun onEvent(event: MarsPhotosViewModelEvent) {
        when (event) {
            is MarsPhotosViewModelEvent.OnRefresh -> {
                fetchMarsPhotos()
            }

            is MarsPhotosViewModelEvent.OnItemClick -> {
                //TODO
            }
        }
    }

    private fun ApiError.toUiText(): UiText {
        return when (this) {
            is ApiError.BackendError ->
                UiText.DynamicString(errorMessage)

            is ApiError.NetworkError ->
                UiText.StringResource(R.string.error_network)

            ApiError.TimeoutError ->
                UiText.StringResource(R.string.error_timeout)

            ApiError.UnknownError ->
                UiText.StringResource(R.string.error_unknown)
        }
    }
}