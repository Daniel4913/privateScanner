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
fun ExtractedInformationFields(
    name: String,
    quantity: String,
    price: String,
    quantityTimesPrice: String,
    nameFocused: Boolean,
    quantityFocused: Boolean,
    priceFocused: Boolean,
    quantityTimesPriceFocused: Boolean,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ExtractedInformationPicker(
                onClick = { },
                name = name,
                onNameChanged = {},
                quantity = quantity,
                onQuantityChanged = {},
                price = price,
                onPriceChanged = {},
                quantityTimesPrice = quantityTimesPrice,
                onQuantityTimesPriceChanged = {},
                nameFocused = {},
                quantityFocused = {},
                priceFocused = {},
                quantityTimesPriceFocused = {},

                )
        }
    }
}

