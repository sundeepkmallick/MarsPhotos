package com.marsphoto.ui.screens

sealed class MarsPhotosViewModelEvent {
    data object OnRefresh : MarsPhotosViewModelEvent()
    data object OnItemClick : MarsPhotosViewModelEvent()
}