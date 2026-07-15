package com.marsphoto.internet

import kotlinx.coroutines.flow.Flow

interface InternetObserver {
    val isConnected: Flow<Boolean>
}