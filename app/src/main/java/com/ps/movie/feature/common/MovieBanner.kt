package com.ps.movie.feature.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ps.movie.R
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieBanner(
    modifier: Modifier,
    testTag: String,
    imagePath: String,
    onMovieClick: (() -> Unit)? = null,
) {
    GlideImage(
        modifier = modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(size = 5.dp))
            .clickable {
                onMovieClick?.invoke()
            }
            .testTag(testTag),
        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
        imageModel = {
            imagePath
        },
        loading = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Gray)
                    .height(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        },
        failure = {
            Image(
                painter = painterResource(id = R.drawable.ic_downloading),
                contentDescription = stringResource(
                    id = R.string.failed_to_load_image,
                ),
            )
        },
        requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE) },
    )
}
