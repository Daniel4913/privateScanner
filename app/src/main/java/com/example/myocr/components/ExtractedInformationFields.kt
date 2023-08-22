package com.example.myocr.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import com.example.myocr.mlkit.GraphicOverlay

@Composable
fun ExtractedInformationFields(name: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ExtractedInformationPicker(
                isSelected = false,
                onClick = { },
                name = name,
                onNameChanged = {

                },
                quantity = "",
                onQuantityChanged = {},
                price = "",
                onPriceChanged = {},
                quantityTimesPrice = "",
                onQuantityTimesPriceChanged = {},
            )
        }
    }
}

