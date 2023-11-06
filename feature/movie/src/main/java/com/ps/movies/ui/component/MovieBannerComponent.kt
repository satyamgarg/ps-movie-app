package com.ps.movies.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ps.movies.R
import com.ps.movies.theme.LocalDimension
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun MovieBanner(
    modifier: Modifier,
    imagePath: String,
    onMovieClick: (() -> Unit)? = null,
) {
    val localDims = LocalDimension.current
    GlideImage(
        modifier = modifier.height(localDims.size400)
            .padding(localDims.padding10).fillMaxWidth(1f)
            .clip(RoundedCornerShape(localDims.size5)).clickable {
                onMovieClick?.invoke()
            },
        imageOptions = ImageOptions(contentScale = ContentScale.FillBounds),
        imageModel = {
            imagePath
        },
        loading = {
            Box(
                modifier = Modifier.fillMaxWidth().background(color = Color.Gray)
                    .height(localDims.size300),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        },
        requestOptions = { RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.RESOURCE) },
        failure = {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = stringResource(id = R.string.failed_to_load_image),
                tint = MaterialTheme.colorScheme.error,
            )
        },
    )
}
