package com.marsphoto.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.marsphoto.domain.model.MarsPhoto

@Composable
fun MarsPhotoItem(marsPhoto: MarsPhoto){
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize()
            .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp )
            .background(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(5.dp)
            )
    ) {
        val (image) = createRefs()

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            model = marsPhoto.imgSrc,
            contentDescription = null,
        )
    }
}