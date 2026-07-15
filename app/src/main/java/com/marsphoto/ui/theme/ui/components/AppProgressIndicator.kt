package com.marsphoto.ui.theme.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppProgressIndicator() {
    Box(
        modifier = Modifier
            .wrapContentSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(100.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}