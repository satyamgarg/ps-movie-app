package com.ps.movies.ui.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.ps.movies.R

@Composable
fun DisplayTitle(title: String) {
    Text(
        text = title.ifEmpty { stringResource(id = R.string.app_name) },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.headlineMedium,
    )
}
