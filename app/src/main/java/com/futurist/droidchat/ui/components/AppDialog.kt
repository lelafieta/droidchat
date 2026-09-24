package com.futurist.droidchat.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.futurist.droidchat.R

@Composable
fun AppDialog(
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    message: String,
    confirmButtonText: String = stringResource(R.string.common_ok),
    title: String? = null,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = onConfirmButtonClick
            ) {
                Text(confirmButtonText)
            }
        },
        title = {
            title?.let {
                Text(it)
            }
        },
        text = {
            Text(message, color = MaterialTheme.colorScheme.onSurface)
        },
    )

}