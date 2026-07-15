package com.marsphoto.ui.screens

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.marsphoto.ui.theme.ui.components.AppProgressIndicator
import com.marsphoto.ui.theme.ui.components.AppTopBarMedium

@Composable
fun MarsPhotosScreen(
    viewModel: MarsPhotosViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasResumedOnce by rememberSaveable { mutableStateOf(false) }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (hasResumedOnce) {
                    viewModel.onEvent(MarsPhotosViewModelEvent.OnRefresh)
                } else {
                    hasResumedOnce = true
                }
            }

        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    MarsPhotos(
        uiState.value,
        onRetry = {
            viewModel.onEvent(MarsPhotosViewModelEvent.OnRefresh)
        }
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarsPhotos(uiState: MarsPhotosUiState, onRetry: () -> Unit) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppTopBarMedium(
                title = "Mars Photos",
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {
            when (uiState) {
                is MarsPhotosUiState.Success -> {
                    val marsPhotos = uiState.marsPhotos
                    LazyColumn() {
                        items(marsPhotos.size){
                            MarsPhotoItem(marsPhotos[it])
                        }
                    }
                }

                is MarsPhotosUiState.Loading -> {
                    AppProgressIndicator()
                }

                is MarsPhotosUiState.Error -> {
                    MarsPhotosError(
                        error = uiState.message.asString(context),
                        onRetry = onRetry
                    )
                }
            }
        }
    }
}